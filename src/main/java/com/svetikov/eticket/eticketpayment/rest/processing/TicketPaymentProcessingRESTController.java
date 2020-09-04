package com.svetikov.eticket.eticketpayment.rest.processing;

import com.svetikov.eticket.eticketpayment.service.processing.impl.TicketPaymentOrderProcessServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/process/payment/ticket")
public class TicketPaymentProcessingRESTController {

    @Autowired
    private TicketPaymentOrderProcessServiceImpl paymentOrderProcessService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<Map<Object, Object>> processTicketPaymentOrder(@PathVariable(name = "paymentId") String paymentId) {
        Map<Object, Object> orderStatus = paymentOrderProcessService.processPayment(paymentId);
        return new ResponseEntity<>(orderStatus, HttpStatus.OK);
    }

}
