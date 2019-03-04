package com.forgeinnovations.android.gitrepoelite.datamodel.GitHubTopRepo;

import com.squareup.moshi.Json;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Rahul B Gautam on 9/8/18.
 */
public class GitHubTopRepoResponse {
    @Json(name = "count")
    private Integer count;
    @Json(name = "items")
    private List<Item> items = null;
    private String errorMessage;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String GetTopRepoShareData(){

        StringBuilder stringBuilder =  new StringBuilder();

        try {
            stringBuilder.append("<html><body>");
            Iterator it = getItems().iterator();

            while (it.hasNext())
            {
                //Map.Entry currItem = (Map.Entry) it.next();
                Item item = (Item)it.next();
                String formattedString = String.format("%s<br/>%s<br/> Stars Count =%s, Watcher Count =%s,Forks Count =%s <br/>%s<br/>",item.getRepo(),item.getDesc(),item.getStars(), item.getAddedStarsValue(),item.getForks(), item.getRepoLink());
                stringBuilder.append(formattedString);
                stringBuilder.append("<hr/>");
            }

            stringBuilder.append("</body></html>");
        }

        catch(Exception ex){

        }

        return  stringBuilder.toString();
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
