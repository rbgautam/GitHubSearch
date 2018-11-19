package com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopRepo;

import com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Owner;
import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class Item {
    @Json(name = "added_stars")
    private String addedStars;
    @Json(name = "avatars")
    private List<String> avatars = null;
    @Json(name = "desc")
    private String desc;
    @Json(name = "forks")
    private String forks;
    @Json(name = "lang")
    private String lang;
    @Json(name = "repo")
    private String repo;
    @Json(name = "repo_link")
    private String repoLink;
    @Json(name = "stars")
    private String stars;

    private boolean IsFavorite;

    public boolean isFavorite() {
        return IsFavorite;
    }

    public void setFavorite(boolean favorite) {
        IsFavorite = favorite;
    }


    public String getAddedStars() {
        return addedStars;
    }

    public void setAddedStars(String addedStars) {
        this.addedStars = addedStars;
    }

    public List<String> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<String> avatars) {
        this.avatars = avatars;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getForks() {
        return forks;
    }

    public void setForks(String forks) {
        this.forks = forks;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getRepoLink() {
        return repoLink;
    }

    public void setRepoLink(String repoLink) {
        this.repoLink = repoLink;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getAddedStarsValue() {
        String strAdded = getAddedStars();
        strAdded = strAdded.replace(" stars this month", "");
        strAdded = strAdded.replace(" stars this week", "");
        strAdded = strAdded.replace(" stars today", "");
        strAdded = strAdded.replace(",", "");
        return strAdded;
    }

    public String getForksValue() {
        String strForks = getForks();
        strForks = strForks.replace(",", "");
        return strForks;
    }

    public com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item convertToSerachItem(Item item){

        com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item convertedObj=  new com.forgeinnovations.android.gitrepoelite.datamodel.GitHubSearch.Item();

        convertedObj.setName(item.getRepo());
        convertedObj.setDescription(item.getDesc());
        convertedObj.setStargazersCount(Integer.valueOf(item.getAddedStarsValue()));
        convertedObj.setForksCount(Integer.valueOf(item.getForksValue()));
        convertedObj.setWatchersCount(0);
        convertedObj.setOwner(new Owner());
        String avatarUrl = item.getAvatars().get(0).replace("?s=40&","?s=80&");

        convertedObj.getOwner().setAvatarUrl(avatarUrl);
        convertedObj.setHtmlUrl(item.getRepoLink());

        return convertedObj;
    }
}


