package com.svetikov.eticket.eticketpayment.rest.status;

import com.svetikov.eticket.eticketpayment.service.reception.TicketPaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/payment/ticket")
public class TicketPaymentOrderStatusRESTController {

    @Autowired
    private TicketPaymentOrderService ticketPaymentOrderService;

    @GetMapping("/status/{id}")
    public ResponseEntity<Map<Object, Object>> getPaymentOrderStatusByPaymentId(@PathVariable(name = "id")String id) {
        Map<Object, Object> response = ticketPaymentOrderService.getPaymentOrderStatusByPaymentId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
