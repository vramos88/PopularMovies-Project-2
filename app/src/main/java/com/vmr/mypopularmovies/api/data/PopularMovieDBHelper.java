package com.vmr.mypopularmovies.api.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Victor Ramos on 4/2/17.
 */

public class PopularMovieDBHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies.db";

    public PopularMovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + PopularMovieContract.PopularMovieEntry.TABLE_NAME
                + " (" +
                PopularMovieContract.PopularMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_VOTE_AVG + " TEXT NOT NULL, " +
                PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                PopularMovieContract.PopularMovieEntry.COLUMN_MOVIE_BACKDROP_PATH + " TEXT NOT NULL " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PopularMovieContract.PopularMovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
