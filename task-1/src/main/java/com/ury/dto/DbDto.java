package com.ury.dto;


import com.ury.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DbDto {

    private List<User> users;
    private List<Credit> credits;
    private List<Discount> discounts;
    private List<Event> events;
    private List<Transaction> transactions;
}
