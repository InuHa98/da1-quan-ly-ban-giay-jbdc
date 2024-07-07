package com.app.utils;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

/**
 *
 * @author inuHa
 */
public class CurrencyUtils {

    public static String parseString(long price) {
        return new DecimalFormat("#,###").format(price).replace(",", ".") + "đ";
    }

    public static long parseNumber(String price) {
        return Long.parseLong(price.replace(".", "").replace("đ", ""));
    }

    public static DefaultFormatterFactory getDefaultFormatVND() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');

        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);

        NumberFormatter formatter = new NumberFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                if (text.isEmpty()) {
                    return null;
                }
                return super.stringToValue(text);
            }
        };

        formatter.setAllowsInvalid(false);
        formatter.setOverwriteMode(true);
        formatter.setFormat(decimalFormat);
        return new DefaultFormatterFactory(formatter);
    }

}
