package com.svetikov.eticket.eticketpayment.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class OrderSentDateValidator implements ConstraintValidator<OrderSentDateConstraint, Date> {

    private final int DAYS_BEFORE = 5;

    private final int DAYS_AFTER = 60;

    @Override
    public void initialize(OrderSentDateConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date == null) {
            throw new IllegalArgumentException("Date can'b be null");
        }

        LocalDate today = LocalDate.now();
        LocalDate orderSentDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (inRange(orderSentDate, today.minusDays(DAYS_BEFORE), today.plusDays(DAYS_AFTER))) {
            return true;
        }
        return false;
    }

    private boolean inRange(LocalDate date, LocalDate from, LocalDate until) {
        return !date.isBefore(from) && !date.isAfter(until);
    }
}
