package com.forgeinnovations.android.gitrepoelite.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopRepo.GitHubTopRepoResponse;
import com.forgeinnovations.android.gitrepoelite.utilities.Utility;
import com.forgeinnovations.android.gitrepoelite.viewmodel.URLService.GitHubTopRepoService;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class TopRepoViewModel extends ViewModel {

    private static final String ERROR_TAG = "API_CALL_ERROR";
    //this is the data that we will fetch asynchronously
    private MutableLiveData<GitHubTopRepoResponse> repoList;

    //we will call this method to get the data
    public LiveData<GitHubTopRepoResponse> GetTopRepos(String language, String since) {
        //if the list is null
        // if (repoList == null) {
        repoList = new MutableLiveData<GitHubTopRepoResponse>();
        //we will load it asynchronously from server in this method
        loadTopRepos(language, since);
        //}
        //finally we will return the list
        return repoList;
    }

    private void loadTopRepos(String language, String since) {

        OkHttpClient client = Utility.GetOkHttpClient();
        Retrofit retrofit = Utility.BuildRetrofitClient(GitHubTopRepoService.BASE_URL, client);

        GitHubTopRepoService api = retrofit.create(GitHubTopRepoService.class);
        Call<GitHubTopRepoResponse> call = api.getGithubRepoData(language, since);

        call.enqueue(new Callback<GitHubTopRepoResponse>() {
            @Override
            public void onResponse(Call<GitHubTopRepoResponse> call, Response<GitHubTopRepoResponse> response) {
                repoList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<GitHubTopRepoResponse> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(ERROR_TAG, errorMessage);
                call.cancel();
                GitHubTopRepoResponse repoResponse = new GitHubTopRepoResponse();
                repoResponse.setErrorMessage(errorMessage);
                repoList.postValue(repoResponse);
            }
        });

    }


}
