package com.forgeinnovations.android.githubelite.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.forgeinnovations.android.githubelite.datamodel.GitHubTopDev.GitHubTopDevResponse;
import com.forgeinnovations.android.githubelite.utilities.Utility;
import com.forgeinnovations.android.githubelite.viewmodel.URLService.GitHubTopDevService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class TopDevViewModel extends ViewModel {

    private static final String ERROR_TAG = "API_CALL_ERROR";
    //this is the data that we will fetch asynchronously
    private MutableLiveData<GitHubTopDevResponse> topDevList;

    //we will call this method to get the data
    public LiveData<GitHubTopDevResponse> GetTopDevs(String language, String since) {
        //if the list is null

        topDevList = new MutableLiveData<GitHubTopDevResponse>();
        //we will load it asynchronously from server in this method
        loadTopDevs(language, since);

        //finally we will return the list
        return topDevList;
    }

    private void loadTopDevs(String language, String since) {
        OkHttpClient client = Utility.GetOkHttpClient();
        Retrofit retrofit = Utility.BuildRetrofitClient(GitHubTopDevService.BASE_URL, client);

        GitHubTopDevService api = retrofit.create(GitHubTopDevService.class);
        Call<GitHubTopDevResponse> call = api.getGithubDevData(language, since);

        call.enqueue(new Callback<GitHubTopDevResponse>() {
            @Override
            public void onResponse(Call<GitHubTopDevResponse> call, Response<GitHubTopDevResponse> response) {
                topDevList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<GitHubTopDevResponse> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(ERROR_TAG, errorMessage);
                call.cancel();
                GitHubTopDevResponse repoResponse = new GitHubTopDevResponse();
                repoResponse.setErrorMessage(errorMessage);
                topDevList.postValue(repoResponse);
            }
        });

    }


}
