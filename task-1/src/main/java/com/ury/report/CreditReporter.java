package com.ury.report;

import com.ury.dto.DbDto;
import com.ury.dto.ReportDto;
import com.ury.model.Credit;
import com.ury.model.Settings;
import com.ury.model.ShowFor;
import com.ury.model.Transaction;
import com.ury.model.User;
import com.ury.print.CreditReportPrinter;
import com.ury.map.CurrencyConverter;
import com.ury.map.ReportDtoComparator;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


public class CreditReporter {

    private static final String STATUS_DONE = "DONE";
    private static final String STATUS_IN_PROGRESS = "IN PROGRESS";

    private final Settings settings;
    private final CreditReportPrinter creditReportPrinter;
    private final CurrencyConverter currencyConverter;
    private final ReportDtoComparator reportDtoComparator;

    public CreditReporter(Settings settings) {
        this.settings = settings;
        this.creditReportPrinter = new CreditReportPrinter();
        this.currencyConverter = new CurrencyConverter(settings);
        this.reportDtoComparator = new ReportDtoComparator();
    }

    public void printFormattedCredits(DbDto db) {
        List<ReportDto> reports = db.getCredits().stream()
                .filter(credit -> filterByShowFor(credit, db))
                .map(credit -> createCreditReport(credit, db))
                .sorted(reportDtoComparator.getComparator(settings.getSortBy()))
                .toList();

        creditReportPrinter.printCredits(reports);
    }

    private boolean filterByShowFor(Credit credit, DbDto db) {
        ShowFor showFor = settings.getShowFor();
        if (showFor == null) {
            return true;
        }

        switch (showFor.getType()) {
            case ID:
                return filterById(credit, showFor);
            case NAME:
                return filterByName(credit, db, showFor);
            default:
                return false;
        }
    }

    private boolean filterById(Credit credit, ShowFor showFor) {
        return showFor.getUsers().contains(String.valueOf(credit.getUserId()));
    }

    private boolean filterByName(Credit credit, DbDto db, ShowFor showFor) {
        return db.getUsers().stream()
                .filter(u -> u.getId() == credit.getUserId())
                .map(u -> u.getName() + " " + u.getSecondName())
                .anyMatch(showFor.getUsers()::contains);
    }

    private ReportDto createCreditReport(Credit credit, DbDto db) {

        String userName = db.getUsers().stream()
                .filter(u -> u.getId() == credit.getUserId())
                .findFirst()
                .map(user -> user.getName() + " " + user.getSecondName())
                .orElse(null);

        List<Transaction> transactions = db.getTransactions().stream()
                .filter(t -> t.getCreditId() == credit.getId())
                .collect(Collectors.toList());

        BigDecimal totalDebt = transactions.stream()
                .map(t -> currencyConverter.convertToRUB(t.getMoney(), t.getCurrency()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String status = totalDebt.compareTo(BigDecimal.ZERO) == 0 ? STATUS_DONE : STATUS_IN_PROGRESS;

        return ReportDto.builder()
                .creditId(credit.getId())
                .userId(credit.getUserId())
                .userName(userName)
                .transactionCount(transactions.size())
                .debt(totalDebt)
                .period(credit.getPeriod().toString())
                .status(status)
                .build();
    }
}
