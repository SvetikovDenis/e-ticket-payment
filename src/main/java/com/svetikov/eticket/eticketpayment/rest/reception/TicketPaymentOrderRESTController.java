package com.svetikov.eticket.eticketpayment.rest.reception;

import com.svetikov.eticket.eticketpayment.dto.model.TicketPaymentOrderDTO;
import com.svetikov.eticket.eticketpayment.dto.request.TicketPaymentRequestOrderDTO;
import com.svetikov.eticket.eticketpayment.mapper.TicketPaymentOrderMapper;
import com.svetikov.eticket.eticketpayment.service.reception.TicketPaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/payment/ticket")
public class TicketPaymentOrderRESTController {

    @Autowired
    private TicketPaymentOrderService ticketPaymentOrderService;

    @Autowired
    private TicketPaymentOrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<List<TicketPaymentOrderDTO>> getAll() {
        List<TicketPaymentOrderDTO> ticketPaymentOrders = orderMapper.convertListToDTO(ticketPaymentOrderService.getAll());
        return new ResponseEntity<>(ticketPaymentOrders, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<Object, Object>> acceptTicketPaymentOrder(@Valid @RequestBody TicketPaymentRequestOrderDTO paymentOrder) {
        Map<Object,Object> response = ticketPaymentOrderService.createPaymentOrder(paymentOrder);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<TicketPaymentOrderDTO>> getAllFutureOrdersByClient(@PathVariable(name = "id")String clientId) {
        List<TicketPaymentOrderDTO> ticketPaymentOrders = orderMapper.convertListToDTO(ticketPaymentOrderService.getAllFutureOrdersByClient(clientId));
        return new ResponseEntity<>(ticketPaymentOrders, HttpStatus.OK);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity deleteTicketPaymentOrderByClient(@PathVariable(name = "id")String clientId) {
        ticketPaymentOrderService.deleteByClientId(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
