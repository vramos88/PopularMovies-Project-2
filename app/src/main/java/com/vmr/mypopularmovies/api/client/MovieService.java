package com.vmr.mypopularmovies.api.client;

import com.vmr.mypopularmovies.api.model.Movie;
import com.vmr.mypopularmovies.api.model.MovieReviews;
import com.vmr.mypopularmovies.api.model.MovieVideos;
import com.vmr.mypopularmovies.api.model.Movies;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public interface MovieService {
    @GET(Endpoints.POPULAR)
    Observable<Movies> getPopularMovies(@Query(QueryParam.API_KEY) String api_key);

    @GET(Endpoints.TOP_RATED)
    Observable<Movies> getTopRatedMovies(@Query(QueryParam.API_KEY) String api_key);

    @GET(Endpoints.MOVIE_VIDEOS)
    Observable<MovieVideos>getMovieVideos(@Path(QueryParam.ID) Integer id,@Query(QueryParam.API_KEY) String api_key);

    @GET(Endpoints.MOVIE_REVIEWS)
    Observable<MovieReviews>getMovieReviews(@Path(QueryParam.ID) Integer id, @Query(QueryParam.API_KEY) String api_key);
}
