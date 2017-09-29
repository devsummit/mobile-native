package io.devsummit.devsummit.Models.userticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ticket_code")
    @Expose
    private Object ticketCode;
    @SerializedName("ticket_id")
    @Expose
    private Object ticketId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(Object ticketCode) {
        this.ticketCode = ticketCode;
    }

    public Object getTicketId() {
        return ticketId;
    }

    public void setTicketId(Object ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
