package io.devsummit.android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import io.devsummit.android.Models.basemodel.Included;
import io.devsummit.android.Models.basemodel.Meta;
import io.devsummit.android.Models.userfeed.FeedData;
import io.devsummit.android.Models.userfeed.Links;

/**
 * Created by wlisrausr on 04/10/17.
 */

public class UserFeedModel implements Serializable {
    @SerializedName("data")
    @Expose
    private List<FeedData> data = null;
    @SerializedName("included")
    @Expose
    private Included included;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<FeedData> getData() {
        return data;
    }

    public void setData(List<FeedData> data) {
        this.data = data;
    }

    public Included getIncluded() {
        return included;
    }

    public void setIncluded(Included included) {
        this.included = included;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }
}
