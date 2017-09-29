package io.devsummit.devsummit.Models;

/**
 * Created by ganesh on 29/09/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.devsummit.devsummit.Models.basemodel.Included;
import io.devsummit.devsummit.Models.basemodel.Links;
import io.devsummit.devsummit.Models.basemodel.Meta;
import io.devsummit.devsummit.Models.userticket.Datum;

public class UserTicketModel {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("included")
    @Expose
    private Included included;
    @SerializedName("links")
    @Expose
    private Links links;
    @SerializedName("meta")
    @Expose
    private Meta meta;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
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