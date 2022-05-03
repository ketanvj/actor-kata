package org.incubyte.actors;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@MicronautTest
public class PersonEndPointTest {
    @Client("/")
    @Inject
    HttpClient httpClient;
    @Test
    public void should_search_for_people_based_on_query() {
        List<SearchResult> results = httpClient.toBlocking().retrieve(HttpRequest.GET("people?query=tom+cruise"), Argument.listOf(SearchResult.class));
        assertThat(results.size()).isGreaterThan(0);
        SearchResult result = results.get(0);
        assertThat(result.getName()).isNotEmpty();
        assertThat(result.getProfilePath()).isNotEmpty();
        assertThat(result.getId()).isEqualTo(500L);
    }

    @Test()
    public void should_return_404_if_person_not_found() {
        HttpClientResponseException httpClientResponseException = assertThrows(
                HttpClientResponseException.class, () -> {
                    List<SearchResult> results = httpClient.toBlocking().retrieve(HttpRequest.GET("people?query=ABC+XYZ"),
                            Argument.listOf(SearchResult.class));
                }
        );
        assertThat( httpClientResponseException.getMessage()).isEqualTo("Not Found");
        assertThat(httpClientResponseException.getResponse().code()).isEqualTo(HttpResponse.notFound().status().getCode());
    }

    @Test
    public void should_return_person_info(){

        Person person = httpClient.toBlocking().retrieve(HttpRequest.GET("people/500"), Argument.of(Person.class));
        assertThat(person.getName()).isEqualTo("Tom Cruise");
        assertThat(person.getImage()).isNotEmpty();
        assertThat(person.getGender()).isEqualTo(2);
        assertThat(person.getPlaceOfBirth()).isEqualTo("Syracuse, New York, USA");
    }

    @Test
    public void should_return_all_movies_for_a_person_id() {

        //Arrange
        //Act
        List<Movie> movies = httpClient.toBlocking().retrieve(HttpRequest.GET("people/500/movie_credits"), Argument.listOf(Movie.class));
        //Assert
        assertThat(movies).hasSize(82);
        movies.get(0).getGetImagePath().equals("*.jpg");


    }
    @Test
    public void should_return_all_tvs_for_a_person_id() {

        //Arrange
        //Act
        List<TVShow> tvs = httpClient.toBlocking().retrieve(HttpRequest.GET("people/500/tv_credits"), Argument.listOf(TVShow.class));
        //Assert
        assertThat(tvs).hasSize(26);
        tvs.get(0).getImagePath().equals("*.jpg");

    }
}
