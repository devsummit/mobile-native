package io.devsummit.devsummit.Models.authmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.devsummit.devsummit.Models.login.Data;
import io.devsummit.devsummit.Models.basemodel.Meta;
import io.devsummit.devsummit.Models.refreshtoken.Included;
import io.devsummit.devsummit.Models.refreshtoken.Links;

/**
 * Created by ganesh on 27/09/17.
 */

public class RefreshTokenModel {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("included")
    @Expose
    private Included included;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
