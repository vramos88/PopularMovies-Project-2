package com.vmr.mypopularmovies.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vmr.mypopularmovies.R;
import com.vmr.mypopularmovies.api.data.PopularMovieContract;
import com.vmr.mypopularmovies.api.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public class MovieListAdapter  extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>  {

    private List<Movie> mMovieList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private MovieListListener mListener;

    public interface MovieListListener{
        void onMovieSelected(Movie movie);
    }

    public MovieListAdapter(Context context, List<Movie> list, MovieListListener listener){
        mInflater = LayoutInflater.from(context);
        mMovieList = list;
        mContext = context;
        mListener = listener;
    }

    public void updateList(List<Movie> list){
        mMovieList = list;
        notifyDataSetChanged();
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_movie_list_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);
        Picasso.with(mContext)
                .load(movie.getPosterURL())
                .into(holder.ivThumbnail);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivThumbnail;

        public ViewHolder(View view) {
            super(view);
            ivThumbnail = (ImageView)view.findViewById(R.id.ivThumbnail);
            ivThumbnail.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mListener.onMovieSelected(mMovieList.get(getAdapterPosition()));
        }
    }


    public void add(Cursor cursor) {
        mMovieList.clear();
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
                Movie movie = new Movie(id, title, posterPath, overview, voteAvg, releaseDate, backdropPath);
                mMovieList.add(movie);
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}