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
package com.example.android.datafrominternet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.datafrominternet.data.GitHubRestAdapter;
import com.example.android.datafrominternet.datamodel.GitHubSeachResponse;
import com.example.android.datafrominternet.datamodel.Item;
import com.example.android.datafrominternet.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    // TODO (12) Create a variable to store a reference to the error message TextView
    private TextView mErrorMessageTextView;


    // TODO (24) Create a ProgressBar variable to store a reference to the ProgressBar
    private ProgressBar mDownloadRequestProgressBar;



    //TODO:S
    //TODO: Add MVP pattern
    //TODO: Add retofit to parse data
    //TODO: ADD cards to display data
    //TODO: Add details if click on card
    //TODO: Navigate to Website
    //TODO: Share link
    //TODO:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
        // TODO (13) Get a reference to the error TextView using findViewById
        mErrorMessageTextView = (TextView) findViewById(R.id.tv_error_message_display);

        // TODO (25) Get a reference to the ProgressBar using findViewById
        mDownloadRequestProgressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);


    }

    // TODO (2) Create a method called makeGithubSearchQuery
    // TODO (3) Within this method, build the URL with the text from the EditText and set the built URL to the TextView
    private void makeGithubSearchQuery(){

        String searchStr = mSearchBoxEditText.getText().toString();
        if(!searchStr.isEmpty()) {

            GitHubSearchQuery repoAsychQuery = new GitHubSearchQuery();

            repoAsychQuery.execute(searchStr);

        }
    }
    // TODO (14) Create a method called showJsonDataView to show the data and hide the error
    private void showJsonDataView(String githubSearchResults){
        StringBuilder strBuilder = new StringBuilder();
        try {
            JSONObject downloadData = new JSONObject(githubSearchResults);
            JSONArray items = downloadData.getJSONArray("items");


            for(int i=0; i< items.length();i++){

                JSONObject itemObj = (JSONObject) items.get(i);
                String formattedLink = "</br>\n"+itemObj.getString("description")+"\n</br>"+itemObj.getString("html_url") +"\n\n</br>";
                strBuilder.append(formattedLink);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mSearchResultsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        mSearchResultsTextView.setText(Html.fromHtml(strBuilder.toString()));
        //mSearchResultsTextView.setText(strBuilder.toString());
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    private void showJsonToPOJODataView(List<Item> githubSearchResults){
        StringBuilder strBuilder = new StringBuilder();
        mUrlDisplayTextView.setText(queryAdapter.urlString);

        try {

            for(Item item: githubSearchResults){
                String formattedLink = "\n"+item.getDescription()+"\n"+item.getHtmlUrl() +"\n";
                strBuilder.append(formattedLink);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //mSearchResultsTextView.setMovementMethod(LinkMovementMethod.getInstance());
        //mSearchResultsTextView.setText(Html.fromHtml(strBuilder.toString()));
        mSearchResultsTextView.setVisibility(View.VISIBLE);
        mSearchResultsTextView.setText(strBuilder.toString());
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }
    // TODO (15) Create a method called showErrorMessage to show the error and hide the data
    private void showErrorMessage(){
        mErrorMessageTextView.setText(R.string.error_message);
        mSearchResultsTextView.setVisibility(View.INVISIBLE);

    }

    GitHubRestAdapter queryAdapter = new GitHubRestAdapter();

    private class GitHubSearchQuery extends AsyncTask<String,Void,GitHubSeachResponse> implements GitHubRestAdapter.AsyncWebResponse {
        private GitHubSeachResponse mGitHubSeachResponse;
        public GitHubRestAdapter.AsyncWebResponse delegate = null;

        @Override
        protected void onPreExecute() {
            mDownloadRequestProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected GitHubSeachResponse doInBackground(String... strings) {

            //GitHubRestAdapter queryAdapter = new GitHubRestAdapter();
            queryAdapter.delegate = this;

            queryAdapter.getGitHubData(strings[0]);
            return mGitHubSeachResponse;

        }

        @Override
        protected void onPostExecute(GitHubSeachResponse githubSearchResults) {
            // TODO (27) As soon as the loading is complete, hide the loading indicator
            mDownloadRequestProgressBar.setVisibility(View.INVISIBLE);
            if (githubSearchResults != null && !(githubSearchResults.getItems().size() == 0)) {
                // TODO (17) Call showJsonDataView if we have valid, non-null results
                showJsonToPOJODataView(githubSearchResults.getItems());

            }else{
                showErrorMessage();
            }
            // TODO (16) Call showErrorMessage if the result is null in onPostExecute
        }


        @Override
        public void processFinish(GitHubSeachResponse githubSearchResults) {
            //GitHubSeachResponse mGitHubSeachResponse = githubSearchResults;

            // TODO (27) As soon as the loading is complete, hide the loading indicator
            mDownloadRequestProgressBar.setVisibility(View.INVISIBLE);
            if (githubSearchResults != null && !(githubSearchResults.getItems().size() == 0)) {
                // TODO (17) Call showJsonDataView if we have valid, non-null results
                showJsonToPOJODataView(githubSearchResults.getItems());

            }else{
                showErrorMessage();
            }
        }
    }

    private class GithubQueryTask extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            mDownloadRequestProgressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(URL... params) {
            String downloadData =  new String();
            URL queryURL  = params[0];
            try {
                downloadData = NetworkUtils.getResponseFromHttpUrl(queryURL);

            } catch (IOException e) {

                e.printStackTrace();
                showErrorMessage();
            }
            return downloadData;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            // TODO (27) As soon as the loading is complete, hide the loading indicator
            mDownloadRequestProgressBar.setVisibility(View.INVISIBLE);
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                // TODO (17) Call showJsonDataView if we have valid, non-null results
                showJsonDataView(githubSearchResults);

            }else{
                showErrorMessage();
            }
            // TODO (16) Call showErrorMessage if the result is null in onPostExecute
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            // TODO (4) Remove the Toast message when the search menu item is clicked
//            Context context = MainActivity.this;
//            String textToShow = "Search clicked";
//            Toast.makeText(context, textToShow, Toast.LENGTH_SHORT).show();
            // TODO (5) Call makeGithubSearchQuery when the search menu item is clicked
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
