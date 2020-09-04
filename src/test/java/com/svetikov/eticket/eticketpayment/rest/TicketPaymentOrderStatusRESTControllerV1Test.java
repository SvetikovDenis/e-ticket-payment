package com.svetikov.eticket.eticketpayment.rest;

import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;

public class TicketPaymentOrderStatusRESTControllerV1Test extends BaseRestTest {

    private final String TEST_PAYMENT_ID = "08d6d9d9-1fdd-4353-88f6-1c0dce40t9f9";

    @Test
    public void checkGetStatusForOrderIdMethodStatus() {
        prepareGet(PAYMENT_STATUS_URL + TEST_PAYMENT_ID)
                .statusCode(SC_OK);
    }

}
