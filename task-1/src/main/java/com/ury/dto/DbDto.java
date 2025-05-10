package com.ury.dto;


import com.ury.model.Credit;
import com.ury.model.Discount;
import com.ury.model.Event;
import com.ury.model.Transaction;
import com.ury.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DbDto {

    private List<User> users;
    private List<Credit> credits;
    private List<Discount> discounts;
    private List<Event> events;
    private List<Transaction> transactions;
}
