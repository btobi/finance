package de.tum.finance;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class Utils {

    public static double calculatePerformance(Map<LocalDate, Map<String, Long>> timeMap, LocalDate t1, LocalDate t2, String stock) {
        return Utils.calculatePerformance(timeMap, t1, t2, Collections.singleton(stock));
    }

    public static double calculatePerformance(Map<LocalDate, Map<String, Long>> timeMap, LocalDate t1, LocalDate t2, Set<String> stocks) {
        double sumT1 = StockOperator.init(timeMap).atDate(t1).filterValues(stocks).getSum();
        double sumT2 = StockOperator.init(timeMap).atDate(t2).filterValues(stocks).getSum();
        return getRateOfChange(sumT1, sumT2);
    }

    public static double getRateOfChange(double l1, double l2) {
        return (l2 - l1) / l1;
    }

}
