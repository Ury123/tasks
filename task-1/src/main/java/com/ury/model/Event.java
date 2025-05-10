package com.ury.model;

import com.ury.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Integer id;
    private Currency currency;
    private BigDecimal cost;
    private LocalDate date;

}
