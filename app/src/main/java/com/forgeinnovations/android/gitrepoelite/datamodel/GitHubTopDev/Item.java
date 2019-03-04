package com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopDev;

import com.squareup.moshi.Json;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class Item {
    @Json(name = "developer_avatar")
    private String developerAvatar;
    @Json(name = "full_name")
    private String fullName;
    @Json(name = "user")
    private String user;
    @Json(name = "user_link")
    private String userLink;

    public String getDeveloperAvatar() {
        return developerAvatar;
    }

    public void setDeveloperAvatar(String developerAvatar) {
        this.developerAvatar = developerAvatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserLink() {
        return userLink;
    }

    public void setUserLink(String userLink) {
        this.userLink = userLink;
    }
}
