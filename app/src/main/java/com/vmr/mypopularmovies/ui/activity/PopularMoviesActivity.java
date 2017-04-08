package com.vmr.mypopularmovies.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.vmr.mypopularmovies.R;
import com.vmr.mypopularmovies.api.data.PopularMovieContract;
import com.vmr.mypopularmovies.api.model.Movie;
import com.vmr.mypopularmovies.presenter.MoviesPresenter;
import com.vmr.mypopularmovies.ui.fragment.MovieDetailsFragment;
import com.vmr.mypopularmovies.ui.fragment.MoviesFragment;
import com.vmr.mypopularmovies.view.MoviesView;

import org.parceler.Parcels;

import java.util.ArrayList;

public class PopularMoviesActivity extends AppCompatActivity implements MoviesView,LoaderManager.LoaderCallbacks<Cursor>, MoviesFragment.MoviesFragmentListener{

    private MoviesPresenter mPresenter;
    private MoviesFragment mMoviesFrag;
    private MovieDetailsFragment mMovieDetailFrag;
    private boolean isSplitScreen = false;
    private Movie mSelectedMovie;
    private ArrayList<Movie> mMovieList;
    private final String EXTRA_MOVIE_SELECTED = "movie_selected";
    private final String EXTRA_MOVIE_LIST = "movie_list";
    private final String EXTRA_SORT_BY = "sort_by";

    private final String TAG_FRAG_MOVIE = "movie_list_frag";
    private final String TAG_FRAG_MOVIE_DETAILS = "movie_details_frag";
    private static final int LOADER_FAVORITES = 0;
    private final static int SORT_TOP_RATED = 1, SORT_POPULAR = 2, SORT_FAVORITE = 3;
    private int mSortBy = SORT_TOP_RATED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movies);
        setTitle(getString(R.string.movies));

        mPresenter = new MoviesPresenter();
        mPresenter.setView(this);

        if(findViewById(R.id.container_frag_movie_details)!=null)
            isSplitScreen = true;

        if(savedInstanceState!=null){
            if(savedInstanceState.containsKey(EXTRA_MOVIE_SELECTED))
                mSelectedMovie = Parcels.unwrap(savedInstanceState.getParcelable(EXTRA_MOVIE_SELECTED));
            if(savedInstanceState.containsKey(EXTRA_MOVIE_LIST))
                mMovieList = Parcels.unwrap(savedInstanceState.getParcelable(EXTRA_MOVIE_LIST));

            mMoviesFrag = (MoviesFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAG_MOVIE);
            if(isSplitScreen){
                mMovieDetailFrag = (MovieDetailsFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAG_MOVIE_DETAILS);
            }

            if (mSortBy==SORT_FAVORITE) {
                getSupportLoaderManager().initLoader(LOADER_FAVORITES, null, this);
            }
        }

        else {

            mMoviesFrag = MoviesFragment.newInstance();
            mMoviesFrag.setListener(this);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_frag_movies, mMoviesFrag, TAG_FRAG_MOVIE)
                    .commit();

            if(isSplitScreen) {
                mMovieDetailFrag = MovieDetailsFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.container_frag_movie_details, mMovieDetailFrag, TAG_FRAG_MOVIE_DETAILS).commit();
            }

            mPresenter.getPopularMovies();
        }

        if(findViewById(R.id.container_frag_movie_details)!=null){
            isSplitScreen = true;

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mSelectedMovie!=null)
            outState.putParcelable(EXTRA_MOVIE_SELECTED, Parcels.wrap(mSelectedMovie));
//        outState.putString(TEXT_VIEW_KEY, mTextView.getText());
        if(mMovieList!=null)
            outState.putParcelable(EXTRA_MOVIE_LIST, Parcels.wrap(mMovieList));
        outState.putInt(EXTRA_SORT_BY,mSortBy);
//        if (mSortBy != ) {
//            getSupportLoaderManager().destroyLoader(FAVORITE_MOVIES_LOADER);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie_list, menu);

        return true;
    }

    @Override public void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        if (getFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.sort_top_rated:
//                fetchMovie(MovieFetchAsync.TOP_RATED);
                if(isSplitScreen)
                    mMovieDetailFrag.hideView();
                if(mSortBy == SORT_FAVORITE){
                    getSupportLoaderManager().destroyLoader(LOADER_FAVORITES);
                }
                mSortBy = SORT_TOP_RATED;
                mPresenter.getTopRatedMovies();
                item.setChecked(true);
                break;
            case R.id.sort_most_popular:
//                fetchMovie(MovieFetchAsync.MOST_POPULAR);
                if(isSplitScreen)
                    mMovieDetailFrag.hideView();
                if(mSortBy == SORT_FAVORITE){
                    getSupportLoaderManager().destroyLoader(LOADER_FAVORITES);
                }
                mSortBy = SORT_POPULAR;
                mPresenter.getPopularMovies();
                item.setChecked(true);
                break;
            case R.id.sort_favorite:
                if(isSplitScreen)
                    mMovieDetailFrag.hideView();
                mSortBy = SORT_FAVORITE;
                getSupportLoaderManager().initLoader(LOADER_FAVORITES, null, this);
                item.setChecked(true);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showEmptyState() {

    }

    @Override
    public void renderMovies(ArrayList<Movie> movies) {
        mMovieList = movies;
        mMoviesFrag.refreshList(mMovieList);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onMovieSelected(Movie movie) {
        if(isSplitScreen){
            mSelectedMovie = movie;
            mMovieDetailFrag.displayMovieDetails(mSelectedMovie);
            mMovieDetailFrag.showView();
        }else{
//            mMovieDetailFrag = MovieDetailsFragment.newInstance(movie);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.container_frag_movies, MovieDetailsFragment.newInstance(movie) ); // give your fragment container id in first parameter
//            transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//            transaction.commit();
            Intent intent = new Intent(this,PopularMovieDetailsActivity.class);
            intent.putExtra(EXTRA_MOVIE_SELECTED,Parcels.wrap(movie));
            startActivity(intent);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                PopularMovieContract.PopularMovieEntry.CONTENT_URI,
                PopularMovieContract.PopularMovieEntry.MOVIE_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mMovieList = mMoviesFrag.getMoviesFromCursor(data);
        mMoviesFrag.refreshList(mMovieList);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
