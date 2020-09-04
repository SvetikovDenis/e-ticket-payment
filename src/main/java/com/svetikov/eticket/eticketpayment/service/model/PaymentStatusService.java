package com.svetikov.eticket.eticketpayment.service.model;

import com.svetikov.eticket.eticketpayment.model.PaymentStatus;

import java.util.List;

public interface PaymentStatusService {

    PaymentStatus getById(Long id);

    PaymentStatus getByName(String name);

    List<PaymentStatus> getAll();

    List<PaymentStatus> getAllExcept(Long id);

}
