package com.ury.map;

import com.ury.model.Settings;
import com.ury.model.enums.Currency;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public class CurrencyConverter {
    private final Settings settings;
    private final Map<Currency, Function<BigDecimal, BigDecimal>> conversionMap;

    public CurrencyConverter(Settings settings) {
        this.settings = settings;
        this.conversionMap = new EnumMap<>(Currency.class);

        conversionMap.put(Currency.EUR, amount -> amount.multiply(settings.getStartCostEUR()));
        conversionMap.put(Currency.USD, amount -> amount.multiply(settings.getStartCostUSD()));
        conversionMap.put(Currency.RUB, amount -> amount);
    }

    public BigDecimal convertToRUB(BigDecimal amount, Currency currency) {
        return conversionMap.getOrDefault(currency, a -> {
            log.error("Неверная валюта {}", currency);
            throw new IllegalArgumentException("Неверная валюта: " + currency);
        }).apply(amount);
    }
}
