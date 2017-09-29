package io.devsummit.devsummit.Models;
/**
 * Created by ganesh on 25/09/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.devsummit.devsummit.Models.login.Data;
import io.devsummit.devsummit.Models.login.Included;
import io.devsummit.devsummit.Models.basemodel.Links;
import io.devsummit.devsummit.Models.basemodel.Meta;

public class LoginModel{

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


