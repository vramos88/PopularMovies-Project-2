package com.vmr.mypopularmovies.ui.fragment;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vmr.mypopularmovies.R;
import com.vmr.mypopularmovies.api.data.PopularMovieContract;
import com.vmr.mypopularmovies.api.model.Movie;
import com.vmr.mypopularmovies.api.model.MovieReview;
import com.vmr.mypopularmovies.api.model.MovieVideo;
import com.vmr.mypopularmovies.presenter.MovieDetailsPresenter;
import com.vmr.mypopularmovies.view.MovieDetailView;

import org.parceler.Parcels;
import org.parceler.transfuse.annotations.Bind;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends Fragment implements MovieDetailView{

    @BindView(R.id.iv_movie_poster) ImageView ivMoviePoster;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_release_date) TextView tvReleaseDate;
    @BindView(R.id.tv_vote_avg) TextView tvVoteAvg;
    @BindView(R.id.tv_overview) TextView tvOverview;
    @BindView(R.id.iv_backdrop) ImageView ivBackDrop;
    @BindView(R.id.ll_container_reviews)ViewGroup mReviewsContainer;
    @BindView(R.id.ll_container_trailers) ViewGroup mTrailerContainer;
    @BindView(R.id.btn_add_favorite) Button btnAddFavorite;
    private boolean isFavorite = false;


    private MovieDetailsPresenter mPresenter;

    private Movie mMovie;

    private Unbinder mUnbinder;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    public static MovieDetailsFragment newInstance(){
        MovieDetailsFragment frag = new MovieDetailsFragment();
        return frag;
    }

    public static MovieDetailsFragment newInstance(Movie movie){
        MovieDetailsFragment frag = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", Parcels.wrap(movie));
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        setRetainInstance(true);
        mPresenter = new MovieDetailsPresenter();
        mPresenter.setView(this);

        mUnbinder = ButterKnife.bind(this, view);
        Bundle args = getArguments();
        if(args!=null&&args.containsKey("movie"))
        {
            mMovie = Parcels.unwrap(args.getParcelable("movie"));

            renderView();
        }
        else if(mMovie!=null)
            renderView();

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
        mPresenter.detachView();
    }

    private void renderView(){
        btnAddFavorite.setVisibility(View.VISIBLE);
            tvOverview.setText(mMovie.getOverview());
            tvReleaseDate.setText(mMovie.getRelease_date());
            tvTitle.setText(mMovie.getTitle());
            tvVoteAvg.setText(String.format(Locale.getDefault(),"%.1f/10",mMovie.getVote_average()));
            Picasso.with(getActivity())
                    .load(mMovie.getPosterURL())
                    .into(ivMoviePoster);

        Picasso.with(getActivity())
                .load(mMovie.getBackdropURL())
                .into(ivBackDrop);


        isFavorite = isFavorite();
        setFavoriteButton();

        mPresenter.getMovieReviews(mMovie.getId());
        mPresenter.getMovieVideos(mMovie.getId());
    }

    public void hideView(){
        if(getView()!=null)
            getView().setVisibility(View.INVISIBLE);
    }

    public void showView(){
        if(getView()!=null)
            getView().setVisibility(View.VISIBLE);
    }

    public void displayMovieDetails(Movie movie){
        mMovie = movie;
        renderView();
    }

    @Override
    public Context getContext(){
        return getActivity();
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
    public void showError(String error) {

    }

    @Override
    public void renderMovieVideos(ArrayList<MovieVideo> videos) {
        for (int i = mTrailerContainer.getChildCount() - 1; i >= 2; i--) {
            mTrailerContainer.removeViewAt(i);
        }


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for(final MovieVideo video : videos){
            View videoView = inflater.inflate(R.layout.layout_trailer_item,mTrailerContainer,false);
            TextView tvTitle = (TextView)videoView.findViewById(R.id.tvTitle);
            ImageView ivImage = (ImageView)videoView.findViewById(R.id.iv_trailer_img);
            tvTitle.setText(video.getName());
            Picasso.with(getActivity())
                    .load(video.getCoverImg())
                    .into(ivImage);

            videoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playTrailer(video.getKey());
                }
            });
            mTrailerContainer.addView(videoView);
        }

        if(videos.size()>0)
            mTrailerContainer.setVisibility(View.VISIBLE);
        else
            mTrailerContainer.setVisibility(View.GONE);
    }

    @Override
    public void renderMovieReviews(ArrayList<MovieReview> reviews) {
        for (int i = mReviewsContainer.getChildCount() - 1; i >= 2; i--) {
            mReviewsContainer.removeViewAt(i);
        }

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        for(MovieReview review : reviews){
            View view = inflater.inflate(R.layout.layout_review_item,mReviewsContainer,false);
            TextView tvAuthor = (TextView)view.findViewById(R.id.tv_review_author);
            TextView tvContent = (TextView)view.findViewById(R.id.tv_review_content);

            tvAuthor.setText(review.getAuthor());
            tvContent.setText(review.getContent());

            mReviewsContainer.addView(view);

        }

        if(reviews.size()>0)
            mReviewsContainer.setVisibility(View.VISIBLE);
        else
            mReviewsContainer.setVisibility(View.GONE);
    }

    private void playTrailer(String id){
        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id)));
    }

    private void setFavoriteButton(){
        if(isFavorite)
            btnAddFavorite.setText("Remove Favorite");
        else
            btnAddFavorite.setText("Add Favorite");
    }

    @OnClick(R.id.btn_add_favorite)
    public void onClickFavorite(View v){
        if(isFavorite)
            removeFavoriteMovie();
        else
            addFavoriteMovie();
    }


    public void removeFavoriteMovie() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                if (isFavorite()) {
                    getContext().getContentResolver().delete(PopularMovieContract.PopularMovieEntry.CONTENT_URI,
                            PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_ID + " = " + mMovie.getId(), null);
                    isFavorite = false;

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                setFavoriteButton();

            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void addFavoriteMovie() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                if (!isFavorite) {
                    ContentValues movieValues = new ContentValues();
                    movieValues.put(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_ID,
                            mMovie.getId());
                    movieValues.put(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_TITLE,
                            mMovie.getTitle());
                    movieValues.put(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_POSTER_PATH,
                            mMovie.getPoster_path());
                    movieValues.put(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_OVERVIEW,
                            mMovie.getOverview());
                    movieValues.put(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_VOTE_AVG,
                            mMovie.getVote_average());
                    movieValues.put(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                            mMovie.getRelease_date());
                    movieValues.put(PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_BACKDROP_PATH,
                            mMovie.getBackdrop_path());
                    getContext().getContentResolver().insert(PopularMovieContract.PopularMovieEntry.CONTENT_URI,
                            movieValues
                    );
                    isFavorite = true;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
//                updateFavoriteButtons();
                setFavoriteButton();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private boolean isFavorite() {
        Cursor movieCursor = getContext().getContentResolver().query(
                PopularMovieContract.PopularMovieEntry.CONTENT_URI,
                new String[]{PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_ID},
                PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_ID + " = " + mMovie.getId(),
                null,
                null);

        if (movieCursor != null && movieCursor.moveToFirst()) {
            movieCursor.close();
            return true;
        } else {
            return false;
        }
    }
}
