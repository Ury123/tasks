package com.ury.map;

import com.ury.dto.ReportDto;
import com.ury.model.enums.SortBy;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

public class ReportDtoComparator {
    private final Map<SortBy, Comparator<ReportDto>> comparatorMap;

    public ReportDtoComparator() {
        comparatorMap = new EnumMap<>(SortBy.class);

        comparatorMap.put(SortBy.NAME, Comparator.comparing(ReportDto::getUserName, Comparator.nullsFirst(Comparator.naturalOrder())));
        comparatorMap.put(SortBy.DEBT, Comparator.comparing(ReportDto::getDebt));
    }

    public Comparator<ReportDto> getComparator(SortBy sortParameter) {
        return comparatorMap.getOrDefault(sortParameter, Comparator.comparing(ReportDto::getCreditId));
    }
}
