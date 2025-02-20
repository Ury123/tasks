package com.ury.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportDto {

    private Integer creditId;
    private Integer userId;
    private String userName;
    private Integer transactionCount;
    private BigDecimal debt;
    private String period;
    private String status;
}
