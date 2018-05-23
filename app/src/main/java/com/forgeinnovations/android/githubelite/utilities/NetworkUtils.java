/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.forgeinnovations.android.githubelite.utilities;

import android.net.Uri;

import com.forgeinnovations.android.githubelite.datamodel.Item;
import com.forgeinnovations.android.githubelite.error.ErrorInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

import static com.forgeinnovations.android.githubelite.utilities.URLManager.GITHUB_API_URL;
import static com.forgeinnovations.android.githubelite.utilities.URLManager.GITHUB_BASE_URL;
import static com.forgeinnovations.android.githubelite.utilities.URLManager.PARAM_QUERY;
import static com.forgeinnovations.android.githubelite.utilities.URLManager.PARAM_SORT;
import static com.forgeinnovations.android.githubelite.utilities.URLManager.sortBy;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {


    /**
     * Builds the URL used to query Github.
     *
     * @param githubSearchQuery The keyword that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(String githubSearchQuery) {
        // TODO (1) Fill in this method to build the proper Github query URL
        Uri builtUri = Uri.parse(GITHUB_BASE_URL + GITHUB_API_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();
        return getURLforUriQuery(builtUri);
    }

    private static URL getURLforUriQuery(Uri builtUri) {
        URL queryUrl = null;
        try {
            queryUrl = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return queryUrl;
    }


    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Using OkHttp client to download query data
     */
    private static String getResponseFromUrl(String url) {
        String downloadData = new String();

        OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            downloadData = response.body().string();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return downloadData;

    }


    public static Retrofit provideRetrofit() {
        OkHttpClient client =
                new OkHttpClient.Builder()
                        .addInterceptor(
                                new ErrorInterceptor()
                        )
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .build();

        return new Retrofit.Builder()
                .client(client)
                .baseUrl(URLManager.GITHUB_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    public static Item ConvertFromJSON(String data) {
        Gson gson = new Gson();
        Item pojo = new Item();

        try {

            pojo = gson.fromJson(data, Item.class);


        } catch (Exception ex) {


        }
        return pojo;

    }


    public static String ConvertToJSON(Item data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); //new Gson(); // Gson
        String json = new String();
        try {

            json = gson.toJson(data); //URLEncoder.encode(gson.toJson(data), "UTF-8");

        } catch (Exception ex) {


        }
        return json;

    }

}