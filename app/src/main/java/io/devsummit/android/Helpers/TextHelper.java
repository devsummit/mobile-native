package io.devsummit.android.Helpers;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wlisrausr on 04/10/17.
 */

public class TextHelper {
    public static String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String toRupiahFormat(Number nominal) {
        NumberFormat format = NumberFormat.getInstance();

        return "Rp " + format.format(nominal);
    }

    public static String dateFormat(String inputDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date formattedDate = null;

        try {
            formattedDate = format.parse(inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("MMM dd, yyyy (hh:mm)");
        String outputDate = format.format(formattedDate);

        return outputDate;
    }
}
