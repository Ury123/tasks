package com.ury.model;

import com.ury.model.enums.ShowForType;
import com.ury.model.enums.SortBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
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

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShowFor {
        private ShowForType type;
        private List<String> users;
    }
}
