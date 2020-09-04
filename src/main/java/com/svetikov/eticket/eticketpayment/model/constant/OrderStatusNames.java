package com.svetikov.eticket.eticketpayment.model.constant;

public class OrderStatusNames {

    private final static String[] STATUSES =
            {
                    "BEGIN_PROCESSING", "IN_PROCESS",
                    "ERROR", "SUCCESSFUL"
            };



    public static String getBeginProcessing() {
        return STATUSES[0];
    }

    public static String getInProcess() {
        return STATUSES[1];
    }

    public static String getError() {
        return STATUSES[2];
    }

    public static String getSuccessful() {
        return STATUSES[3];
    }


}

