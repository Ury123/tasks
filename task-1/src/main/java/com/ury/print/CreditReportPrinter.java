package com.ury.print;

import com.ury.dto.ReportDto;

import java.util.List;
import java.util.Optional;

public class CreditReportPrinter {

    public void printCredits(List<ReportDto> reports) {
        System.out.printf("%-10s %-10s %-20s %-15s %-10s %-15s %-20s%n",
                "Credit ID", "User ID", "Name", "Transactions", "Debt", "Period", "Status");

        for (ReportDto report : reports) {
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
}
