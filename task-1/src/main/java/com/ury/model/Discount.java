package com.ury.model;

import com.ury.model.enums.TypeOfDiscount;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Discount {

    private int id;
    private TypeOfDiscount type;
    private LocalDate date;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private double amount;

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
