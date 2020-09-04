package com.svetikov.eticket.eticketpayment.model.comparator;

import com.svetikov.eticket.eticketpayment.model.TicketPaymentOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class PaymentOrderSentTimeComparer implements Comparator<TicketPaymentOrder> {


    private final SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");

    @Override
    public int compare(TicketPaymentOrder o1, TicketPaymentOrder o2) {

        Date date1;
        Date date2;

        int result = 0;
        try {
            date1 = dateFormat.parse(o1.getOrderSentTime());
            date2 = dateFormat.parse(o2.getOrderSentTime());
            result = date1.after(date2) ? 1 : -1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return result;
    }


}
