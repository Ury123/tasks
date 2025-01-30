package com.ury.report;

import com.ury.dto.CreditReport;
import com.ury.model.*;
import com.ury.model.enums.Currency;
import com.ury.model.enums.SortBy;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CreditReporter {

    private final Settings settings;

    public CreditReporter(Settings settings) {
        this.settings = settings;
    }

    public void printFormattedCredits(Db db) {
        List<CreditReport> reports = db.getCredits().stream()
                .filter(credit -> filterByShowFor(credit, db))
                .map(credit -> createCreditReport(credit, db))
                .sorted(getComparator(settings.getSortBy()))
                .toList();

        System.out.printf("%-10s %-10s %-20s %-15s %-10s %-15s %-20s%n",
                "Credit ID", "User ID", "Name", "Transactions", "Debt", "Period", "Status");

        for (CreditReport report : reports) {
            System.out.printf("%-10d %-10d %-20s %-15d %-10s %-15s %-20s%n",
                    report.getCreditId(),
                    Optional.ofNullable(report.getUserId()).orElse(null),
                    Optional.ofNullable(report.getUserName()).orElse(""),
                    report.getTransactionCount(),
                    report.getDebt().toString(),
                    Optional.ofNullable(report.getPeriod()).orElse(""),
                    report.getStatus());
        }
    }

    private boolean filterByShowFor(Credit credit, Db db) {
        Settings.ShowFor showFor = settings.getShowFor();
        if (showFor == null) {
            return true;
        }

        switch (showFor.getType()) {
            case ID:
                return showFor.getUsers().contains(String.valueOf(credit.getUserId()));
            case NAME:
                User user = db.getUsers().stream()
                        .filter(u -> u.getId() == credit.getUserId())
                        .findFirst()
                        .orElse(null);
                if (user == null) {
                    return false;
                }
                String fullName = user.getName() + " " + user.getSecondName();
                return showFor.getUsers().contains(fullName);
            default:
                return false;
        }
    }

    private CreditReport createCreditReport(Credit credit, Db db) {
        User user = db.getUsers().stream()
                .filter(u -> u.getId() == credit.getUserId())
                .findFirst()
                .orElse(null);

        List<Transaction> transactions = db.getTransactions().stream()
                .filter(t -> t.getCreditId() == credit.getId())
                .collect(Collectors.toList());

        BigDecimal totalDebt = transactions.stream()
                .map(t -> convertToRUB(t.getMoney(), t.getCurrency()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String status = totalDebt.compareTo(BigDecimal.ZERO) == 0 ? "DONE" : "IN PROGRESS";

        return new CreditReport(
                credit.getId(),
                credit.getUserId(),
                user != null ? user.getName() + " " + user.getSecondName() : null,
                transactions.size(),
                totalDebt,
                credit.getPeriod().toString(),
                status
        );
    }

    private BigDecimal convertToRUB(BigDecimal amount, Currency currency) {
        switch (currency) {
            case EUR:
                return amount.multiply(settings.getStartCostEUR());
            case USD:
                return amount.multiply(settings.getStartCostUSD());
            case RUB:
                return amount;
            default:
                throw new IllegalArgumentException("Неверная валюта: " + currency);
        }
    }

    private Comparator<CreditReport> getComparator(SortBy sortParameter) {
        switch (sortParameter) {
            case NAME:
                return Comparator.comparing(CreditReport::getUserName, Comparator.nullsFirst(Comparator.naturalOrder()));
            case DEBT:
                return Comparator.comparing(CreditReport::getDebt);
            default:
                return Comparator.comparing(CreditReport::getCreditId);
        }
    }
}
