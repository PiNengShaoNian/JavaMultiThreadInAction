package ch6.so;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UnsafeServlet {
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    protected void doPost(String strExpiryDate) {
        try {
            sdf.parse(strExpiryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}