package com.svetikov.eticket.eticketpayment.service.model.impl;

import com.svetikov.eticket.eticketpayment.exception.db.EntityNotFoundException;
import com.svetikov.eticket.eticketpayment.model.PaymentStatus;
import com.svetikov.eticket.eticketpayment.repository.PaymentStatusRepository;
import com.svetikov.eticket.eticketpayment.service.model.PaymentStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentStatusServiceImpl implements PaymentStatusService {

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    private final Logger log = LoggerFactory.getLogger(PaymentStatusServiceImpl.class);

    @Override
    public PaymentStatus getById(Long id) {
        PaymentStatus paymentStatus = paymentStatusRepository.findById(id).orElse(null);
        if (paymentStatus == null) {
            log.warn("In getById - Payment status with id : {} was not found",id);
            throw new EntityNotFoundException(PaymentStatus.class, "id", id.toString());
        }
        return paymentStatus;
    }

    @Override
    public PaymentStatus getByName(String name) {
        PaymentStatus paymentStatus = paymentStatusRepository.findByName(name);
        if (paymentStatus == null) {
            log.warn("In getById - Payment status with name : {} was not found",name);
            throw new EntityNotFoundException(PaymentStatus.class, "id", name);
        }
        return paymentStatus;
    }

    @Override
    public List<PaymentStatus> getAll() {
        return paymentStatusRepository.findAll();
    }

    @Override
    public List<PaymentStatus> getAllExcept(Long id) {
        return paymentStatusRepository.findAllByIdIsNot(id);
    }

}
