package org.incubyte.actors;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

import java.util.Optional;

@Client("https://api.themoviedb.org/3/")
public interface TmbdClient {
    @Get("search/person{?query}{&api_key}")
    Optional<Page> searchByName(@QueryValue String query, @QueryValue String api_key);

    @Get("person/{id}{?api_key}")
    Optional<Person> getById(int id, @QueryValue String api_key);


    @Get("person/{id}/movie_credits{?api_key}")
    Optional<MovieResult> getMoviesById(int id, @QueryValue String api_key);

    @Get("person/{id}/tv_credits{?api_key}")
    Optional<TvResult> getTvShowsById(int id, String api_key);
}
