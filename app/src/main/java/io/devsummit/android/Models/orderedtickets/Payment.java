package io.devsummit.android.Models.orderedtickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Payment {

    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("expired_at")
    @Expose
    private String expiredAt;
    @SerializedName("fraud_status")
    @Expose
    private String fraudStatus;
    @SerializedName("gross_amount")
    @Expose
    private Integer grossAmount;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("masked_card")
    @Expose
    private Object maskedCard;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("saved_token_id")
    @Expose
    private Object savedTokenId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("transaction_time")
    @Expose
    private String transactionTime;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("va_number")
    @Expose
    private String vaNumber;

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getFraudStatus() {
        return fraudStatus;
    }

    public void setFraudStatus(String fraudStatus) {
        this.fraudStatus = fraudStatus;
    }

    public Integer getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Integer grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getMaskedCard() {
        return maskedCard;
    }

    public void setMaskedCard(Object maskedCard) {
        this.maskedCard = maskedCard;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Object getSavedTokenId() {
        return savedTokenId;
    }

    public void setSavedTokenId(Object savedTokenId) {
        this.savedTokenId = savedTokenId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getVaNumber() {
        return vaNumber;
    }

    public void setVaNumber(String vaNumber) {
        this.vaNumber = vaNumber;
    }

}