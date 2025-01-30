package com.ury.model;

import com.ury.model.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    private int id;
    private LocalDate date;
    private int userId;
    private int creditId;
    private Currency currency;
    private BigDecimal money;
}
