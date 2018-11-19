package com.forgeinnovations.android.gitrepoelite.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;


import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopLanguage.GitHubTopLangResponse;
import com.forgeinnovations.android.gitrepoelite.utilities.Utility;
import com.forgeinnovations.android.gitrepoelite.viewmodel.URLService.GitHubTopLanguageService;


import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class TopLanguageViewModel extends ViewModel {

    private static final String ERROR_TAG = "API_CALL_ERROR";
    //this is the data that we will fetch asynchronously
    private MutableLiveData<GitHubTopLangResponse> languageList;

    //we will call this method to get the data
    public LiveData<GitHubTopLangResponse> GetTopRepos(String language, String since) {
        //if the list is null
        if (languageList == null) {
            languageList = new MutableLiveData<GitHubTopLangResponse>();
            //we will load it asynchronously from server in this method
            loadTopLanguages();
        }
        //finally we will return the list
        return languageList;
    }

    private void loadTopLanguages() {
        OkHttpClient client = Utility.GetOkHttpClient();
        Retrofit retrofit = Utility.BuildRetrofitClient(GitHubTopLanguageService.BASE_URL, client);

        GitHubTopLanguageService api = retrofit.create(GitHubTopLanguageService.class);
        Call<GitHubTopLangResponse> call = api.getGithubLangData();

        call.enqueue(new Callback<GitHubTopLangResponse>() {
            @Override
            public void onResponse(Call<GitHubTopLangResponse> call, Response<GitHubTopLangResponse> response) {
                languageList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<GitHubTopLangResponse> call, Throwable t) {
                String errorMessage = t.getMessage();
                Log.e(ERROR_TAG,errorMessage);
            }
        });

    }


}