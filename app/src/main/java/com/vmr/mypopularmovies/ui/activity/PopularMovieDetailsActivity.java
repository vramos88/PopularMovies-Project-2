package com.vmr.mypopularmovies.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.vmr.mypopularmovies.R;
import com.vmr.mypopularmovies.api.model.Movie;
import com.vmr.mypopularmovies.ui.fragment.MovieDetailsFragment;

import org.parceler.Parcels;

public class PopularMovieDetailsActivity extends AppCompatActivity {

    private final String MOVIE_SELECTED = "movie_selected";
    private final String TAG_FRAG_MOVIE_DETAILS = "movie_details_frag";
    private Movie mSelectedMovie;
    private MovieDetailsFragment mMovieDetailFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular_movie_details);
        setTitle(getString(R.string.movie_details));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState==null) {

            Bundle extras = getIntent().getExtras();
            if (extras != null && extras.containsKey(MOVIE_SELECTED))
                mSelectedMovie = Parcels.unwrap(extras.getParcelable(MOVIE_SELECTED));

            mMovieDetailFrag = MovieDetailsFragment.newInstance(mSelectedMovie);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_frag_movie_details, mMovieDetailFrag, TAG_FRAG_MOVIE_DETAILS).commit();


        }
        else
        {
            if(savedInstanceState.containsKey(MOVIE_SELECTED))
                mSelectedMovie = Parcels.unwrap(savedInstanceState.getParcelable(MOVIE_SELECTED));
            mMovieDetailFrag = (MovieDetailsFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAG_MOVIE_DETAILS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mSelectedMovie!=null)
            outState.putParcelable(MOVIE_SELECTED, Parcels.wrap(mSelectedMovie));

        // call superclass to save any view hierarchy

    }
}
