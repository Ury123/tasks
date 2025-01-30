package com.ury.model;

import com.ury.model.enums.Period;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credit {

    private int id;
    private int userId;
    private LocalDate date;
    private Period period;
    private BigDecimal money;
    private double rate;
}
