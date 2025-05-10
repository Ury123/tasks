package com.ury.model;

import com.ury.model.enums.ShowForType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowFor {
    private ShowForType type;
    private List<String> users;
}
