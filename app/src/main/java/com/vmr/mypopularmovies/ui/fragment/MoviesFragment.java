package com.vmr.mypopularmovies.ui.fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vmr.mypopularmovies.R;
import com.vmr.mypopularmovies.api.data.PopularMovieContract;
import com.vmr.mypopularmovies.api.model.Movie;
import com.vmr.mypopularmovies.ui.adapter.MovieListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements MovieListAdapter.MovieListListener{

    @BindView(R.id.rv_movies) RecyclerView mMoviesList;
    private MovieListAdapter mAdapter;
    private Unbinder mUnbinder;
    private ArrayList<Movie> mMovies;
    private MoviesFragmentListener mListener;


    public interface MoviesFragmentListener{
        void onMovieSelected(Movie movie);
    }

    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance(){
        MoviesFragment frag = new MoviesFragment();
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movies, container, false);
        mUnbinder = ButterKnife.bind(this, v);
        setupRecyclerView();
        setRetainInstance(true);
        return v;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void setListener(MoviesFragmentListener listener){
        mListener = listener;
    }

    private void setupRecyclerView(){
        int columns = getResources().getInteger(R.integer.gallery_columns);
        RecyclerView.LayoutManager layoutManager;
        layoutManager = new GridLayoutManager(getActivity(), columns);
        mMoviesList.setLayoutManager(layoutManager);
        mAdapter = new MovieListAdapter(getActivity(),new ArrayList<Movie>(),this);
        mMoviesList.setAdapter(mAdapter);

        if(mMovies!=null)
            mAdapter.updateList(mMovies);
    }

    public void refreshList(ArrayList<Movie> movies){
        mMovies = movies;
        mAdapter.updateList(movies);
    }

    @Override
    public void onMovieSelected(Movie movie) {
        mListener.onMovieSelected(movie);
    }

    public ArrayList<Movie> getMoviesFromCursor(Cursor cursor){
        ArrayList<Movie> movies = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            int i_id = cursor.getColumnIndex(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_ID);
            int i_title = cursor.getColumnIndex(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_TITLE);
            int i_release_date = cursor.getColumnIndex(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_RELEASE_DATE);
            int i_overview = cursor.getColumnIndex(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_OVERVIEW);
            int i_vote_avg = cursor.getColumnIndex(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_VOTE_AVG);
            int i_poster_path = cursor.getColumnIndex(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_POSTER_PATH);
            int i_backdrop_path = cursor.getColumnIndex(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_BACKDROP_PATH);
            do {
                int id = cursor.getInt(i_id);
                String title = cursor.getString(i_title);
                String posterPath = cursor.getString(i_poster_path);
                String overview = cursor.getString(i_overview);
                String voteAvg = cursor.getString(i_vote_avg);
                String releaseDate = cursor.getString(i_release_date);
                String backdropPath = cursor.getString(i_backdrop_path);
                Movie movie = new Movie(id, title, releaseDate,  overview, voteAvg, posterPath,  backdropPath);
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        return movies;
    }
}
