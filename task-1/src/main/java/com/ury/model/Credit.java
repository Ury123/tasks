package com.ury.model;

import com.ury.model.enums.Period;
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
public class Credit {

    private Integer id;
    private Integer userId;
    private LocalDate date;
    private Period period;
    private BigDecimal money;
    private Double rate;
}
