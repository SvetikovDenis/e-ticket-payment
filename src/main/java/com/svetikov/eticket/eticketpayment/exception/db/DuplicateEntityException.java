package com.svetikov.eticket.eticketpayment.exception.db;

import org.springframework.util.StringUtils;

public class DuplicateEntityException extends RuntimeException {

    public DuplicateEntityException(Class clazz, String duplicateField,String duplicateValue) {
        super(DuplicateEntityException.generateMessage(clazz.getSimpleName(),duplicateField,duplicateValue));

    }

    private static String generateMessage(String entity, String keyParam,String keyValue) {
        return StringUtils.capitalize(entity) +
                " duplicate value for column " +
                keyParam + " with value " + keyValue;
    }
}
