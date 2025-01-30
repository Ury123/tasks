package com.ury.model;

import com.ury.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private int id;
    private Currency currency;
    private BigDecimal cost;
    private LocalDate date;

}
