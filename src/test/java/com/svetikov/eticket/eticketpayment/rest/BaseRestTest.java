package com.svetikov.eticket.eticketpayment.rest;

import com.svetikov.eticket.eticketpayment.ETicketPaymentApplication;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = ETicketPaymentApplication.class,
        webEnvironment = RANDOM_PORT)
public class BaseRestTest {


    static final String PROCESS_PAYMENT_URL = "/api/v1/process/payment/ticket/";
    static final String RECEPTION_PAYMENT_URL = "/api/v1/payment/ticket";
    static final String CLIENT_FUTURE_PAYMENT_ORDERS_URL = "/api/v1/payment/ticket/client/";
    static final String PAYMENT_STATUS_URL = "/api/v1/payment/ticket/status/";
    static final String DUMMY_TEST_JSON = "{ \"Test\": \"Test\" }";
    static final String TICKET_PAYMENT_ORDER_DTO_SCHEMA = "schemas/ticket_payment_order_dto.json";

    static final long ENDPOINT_RESPONSE_TIME = 200L;

    @LocalServerPort
    private int port;
    private String HOST_ROOT = "http://localhost:8080";


    ValidatableResponse prepareGet(String path) {
        return prepareGetDeleteWhen()
                .get(path)
                .then();
    }

    ValidatableResponse prepareGetWithNoAuth(String path) {
        return prepareGetDeleteWithNoAuthWhen()
                .get(path)
                .then();
    }

    ValidatableResponse prepareDelete(String path) {
        return prepareGetDeleteWhen()
                .delete(HOST_ROOT + path)
                .then();
    }

    Response preparePut(String path, String body) {
        return preparePostPutWhen(body)
                .put(HOST_ROOT + path);
    }

    Response preparePost(String path, String body) {
        return preparePostPutWhen(body)
                .post(HOST_ROOT + path);
    }

    private RequestSpecification preparePostPutWhen(String body) {
        return given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(body)
                .when();
    }

    private RequestSpecification prepareGetDeleteWhen() {
        return given()
                .port(port)
                .when();
    }

    private RequestSpecification prepareGetDeleteWithNoAuthWhen() {
        return given()
                .port(port)
                .when();
    }


}
