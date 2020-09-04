package com.svetikov.eticket.eticketpayment.repository;

import com.svetikov.eticket.eticketpayment.model.TicketPaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketPaymentOrderRepository extends JpaRepository<TicketPaymentOrder,Long> {

    TicketPaymentOrder findByPaymentId(String id);

    TicketPaymentOrder findByExternalOrderId(String id);

    @Query(value = "select *  from ticket_payment_order where status_id = ? or status_id = ? order by order_sent_time asc limit 1",nativeQuery = true)
    TicketPaymentOrder findReadyProcessOrder(Long statusId1, Long statusId2);

    @Query(value = "select * from ticket_payment_order where client_id = ? and order_sent_time > now()",nativeQuery = true)
    List<TicketPaymentOrder> findAllFutureOrdersByClient(String id);

    List<TicketPaymentOrder> findAllByClientId(String id);

    void deleteByClientId(String id);

}
