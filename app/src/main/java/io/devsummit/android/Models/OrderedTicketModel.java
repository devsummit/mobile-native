package io.devsummit.android.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.devsummit.android.Models.basemodel.Included;
import io.devsummit.android.Models.basemodel.Links;
import io.devsummit.android.Models.basemodel.Meta;
import io.devsummit.android.Models.orderedtickets.Datum;

/**
 * Created by wlisrausr on 10/2/17.
 */

public class OrderedTicketModel {
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("included")
    @Expose
    private Included included;
    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("links")
    @Expose
    private Links links;

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
