package com.ury.model;

import com.ury.model.enums.TypeOfDiscount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Discount {

    private Integer id;
    private TypeOfDiscount type;
    private LocalDate date;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Double amount;

    public Discount(int id, TypeOfDiscount type, LocalDate date,  double amount) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.amount = amount;
    }

    public Discount(int id, TypeOfDiscount type, LocalDate dateFrom, LocalDate dateTo, double amount) {
        this.id = id;
        this.type = type;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.amount = amount;
    }
}
