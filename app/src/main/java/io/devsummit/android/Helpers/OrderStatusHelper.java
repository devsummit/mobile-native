package io.devsummit.android.Helpers;

import io.devsummit.android.Models.orderedtickets.Payment;

/**
 * Created by wlisrausr on 10/2/17.
 */

public class OrderStatusHelper {
    private static String message;

    public static String convertOrderStatus(Payment payment) {
        if (payment != null) {
            if (payment.getFraudStatus().equals("accept") && payment.getTransactionStatus().equals("capture")) {
                message = "Paid";
            } else if (payment.getFraudStatus().equals("challenge")) {
                message = "Waiting for authori  zation";
            } else if (payment.getTransactionStatus().equals("pending")) {
                message = "Pending";
            } else {
                message = payment.getTransactionStatus();
            }
        } else {
            message = "Not paid";
        }

        return message;
    }
}
