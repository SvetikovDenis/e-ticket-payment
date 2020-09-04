package com.svetikov.eticket.eticketpayment.dto.request;

import com.svetikov.eticket.eticketpayment.dto.AbstractDTO;
import lombok.Data;

@Data
public class PaymentOrderStatusDTO extends AbstractDTO {
    private String status;
}
