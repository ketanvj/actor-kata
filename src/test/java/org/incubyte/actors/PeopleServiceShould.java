package org.incubyte.actors;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceShould {
    @Mock
    TmbdClient tmbdClient;
    Page page;
    Page page2;

    Movie movie1, movie2;
    private final Clock clock = Clock.fixed(ZonedDateTime.parse("2021-10-25T00:00:00.000+09:00[Asia/Seoul]").toInstant(), ZoneId.of("Asia/Seoul"));
    Person person;

    @BeforeEach
    public void init() {
        page = new Page();
        // create result object
        SearchResult searchResult1 = new SearchResult();
        searchResult1.setName("Tom Cruise");
        searchResult1.setProfilePath("/Abc");
        SearchResult searchResult2 = new SearchResult();
        searchResult2.setName("Tom Cruise123");
        searchResult2.setProfilePath("/xyz");
        //add result object to list
        List<SearchResult> allResults = new ArrayList<SearchResult>();
        allResults.add(searchResult1);
        allResults.add(searchResult2);
        page.setResults(allResults);

        page2 = new Page();
        page2.setResults(new ArrayList<>());

        person = new Person();
        person.setBirthday("1962-07-03");
        movie1 = new Movie();
        movie1.setName("Rain Main");
        movie2 = new Movie();
        movie2.setName("Eyes Wide Shut");



    }

    @Test
    void invoke_http_client() {
        when(tmbdClient.searchByName("tom cruise", null)).thenReturn(Optional.of(page));
        PeopleService peopleService = new PeopleService(tmbdClient);

        peopleService.searchByName("tom cruise");
        verify(tmbdClient).searchByName("tom cruise", null);
    }

    @Test
    public void return_empty_optional_when_no_results_found() {
        when(tmbdClient.searchByName("abc xyz", null)).thenReturn(Optional.of(page2));
        PeopleService peopleService = new PeopleService(tmbdClient);
        Optional<List<SearchResult>> results = peopleService.searchByName("abc xyz");
        assertThat(results.isPresent()).isFalse();
    }

    @Test
    public void invoke_http_client_to_retrieve_person() {
        when(tmbdClient.getById(500, null)).thenReturn(Optional.of(person));
        var mockClock = mockStatic(Clock.class);
        when(clock.systemDefaultZone()).thenReturn(clock);
        PeopleService peopleService = new PeopleService(tmbdClient);
        Optional<Person> person = peopleService.getById(500);
        assertThat(person.get().getAge()).isEqualTo(59);
    }


    @Test
    public void invoke_http_client_to_retrieve_movies_for_a_given_id() {
        List<Movie> movies = new ArrayList<>();
        PeopleService peopleService = new PeopleService(tmbdClient);
        Optional<List<Movie>> retrievedMovies = peopleService.getMoviesById(500);
        verify(tmbdClient).getMoviesById(500, null);
    }

}
