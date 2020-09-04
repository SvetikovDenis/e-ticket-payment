package com.svetikov.eticket.eticketpayment.dto.request;

import com.svetikov.eticket.eticketpayment.dto.AbstractDTO;
import com.svetikov.eticket.eticketpayment.validator.OrderSentDateConstraint;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
public class TicketPaymentRequestOrderDTO extends AbstractDTO {

    @NotBlank(message = "Order id can'b be null or blank")
    @Size(min = 5, max = 25, message = "Order id can't be less than 5 symbols or more than 25")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Order id should consist characters and numbers only")
    private String orderId;

    @NotBlank(message = "Route number can'b be null or blank")
    @Size(min = 5, max = 25, message = "Route number can't be less than 5 symbols or more than 25")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Route number should consist characters and numbers only")
    private String routeNumber;

    @NotBlank(message = "Client identifier must be not null")
    @Size(min = 5, max = 20, message = "Client identifier can't be less than 5 symbols or more than 20")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Client identifier should consist characters and numbers only")
    private String clientId;

    @NotNull
    @OrderSentDateConstraint
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date orderSentTime;
}
