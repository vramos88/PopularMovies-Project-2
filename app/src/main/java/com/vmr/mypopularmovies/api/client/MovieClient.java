package com.vmr.mypopularmovies.api.client;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Victor Ramos on 3/5/17.
 */

public class MovieClient {
    private MovieClient(){}

    public static MovieService create() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    // add your other interceptors …

    // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Endpoints.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(MovieService.class);
    }
}
