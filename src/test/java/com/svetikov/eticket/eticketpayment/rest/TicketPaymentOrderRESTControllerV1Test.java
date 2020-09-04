package com.svetikov.eticket.eticketpayment.rest;

import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.lessThan;

public class TicketPaymentOrderRESTControllerV1Test extends BaseRestTest {


    private final String TEST_CLIENT_ID = "1234TEST1234";
    private final String TEST_CREATED_CLIENT_ID = "TEST12345TEST";
    private final String WRONG_CLIENT_ID = "123TEST123";
    private final String TEST_PAYMENT_ORDER_JSON =
            "{\"orderId\": \"2G3HYDS56YU\"," +
            "\"routeNumber\": \"TYRGHP456\"," +
            "\"clientId\": \"TEST12345TEST\", " +
            "\"orderSentTime\": \"2020-10-31T13:34:05+04:00\"}";

    private final String ORDER_ID_NAME = "Payment_id";
    private final Integer ORDER_ID_LENGTH = 36;

    @Test
    public void checkSchemaValidity() {
        prepareGet(RECEPTION_PAYMENT_URL)
                .assertThat()
                .body(matchesJsonSchemaInClasspath(TICKET_PAYMENT_ORDER_DTO_SCHEMA));
    }

    @Test
    public void checkGetAllTicketPaymentOrdersEndpointStatus() {
        prepareGet(RECEPTION_PAYMENT_URL)
                .statusCode(SC_OK);
    }

    @Test
    public void checkGetAllTicketPaymentOrdersForClientIdEndpointStatus() {
        prepareGet(CLIENT_FUTURE_PAYMENT_ORDERS_URL + TEST_CLIENT_ID)
                .statusCode(SC_OK);
    }

    @Test
    public void checkResponseTimeAll() {
        prepareGet(RECEPTION_PAYMENT_URL)
                .time(lessThan(ENDPOINT_RESPONSE_TIME));
    }

    @Test
    public void checkGetAllFutureTicketPaymentOrdersForWrongClientIdEndpointStatus() {
        prepareGet(CLIENT_FUTURE_PAYMENT_ORDERS_URL + WRONG_CLIENT_ID)
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    public void checkBrokenPostMethodStatus() {
        preparePost(RECEPTION_PAYMENT_URL, DUMMY_TEST_JSON)
                .then()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    public void checkPostMethod() {
        preparePost(RECEPTION_PAYMENT_URL, TEST_PAYMENT_ORDER_JSON)
                .then()
                .statusCode(SC_CREATED)
                .body(ORDER_ID_NAME, hasLength(ORDER_ID_LENGTH));

        prepareDelete(CLIENT_FUTURE_PAYMENT_ORDERS_URL + TEST_CREATED_CLIENT_ID)
                .statusCode(SC_NO_CONTENT);
    }



}
