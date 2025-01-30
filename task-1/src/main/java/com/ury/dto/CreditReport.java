package com.ury.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditReport {

    private int creditId;
    private int userId;
    private String userName;
    private int transactionCount;
    private BigDecimal debt;
    private String period;
    private String status;
}
