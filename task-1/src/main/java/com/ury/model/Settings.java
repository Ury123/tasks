package com.ury.model;

import com.ury.model.enums.SortBy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Settings {

    private LocalDate dateFrom;
    private LocalDate dateTo;
    private SortBy sortBy;
    private List<String> useDepartments;
    private BigDecimal startCostEUR;
    private BigDecimal startCostUSD;
    private ShowFor showFor;
}
