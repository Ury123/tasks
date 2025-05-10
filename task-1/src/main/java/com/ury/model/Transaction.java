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
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private Integer id;
    private LocalDate date;
    private Integer userId;
    private Integer creditId;
    private Currency currency;
    private BigDecimal money;
}
