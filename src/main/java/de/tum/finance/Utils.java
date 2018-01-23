package de.tum.finance;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Utils {

    public static double calculatePerformance(Map<LocalDate, Map<String, Long>> timeMap, LocalDate t1, LocalDate t2, List<String> stocks) {
        long sumT1 = StockOperator.init(timeMap).atDate(t1).getSum();
        long sumT2 = StockOperator.init(timeMap).atDate(t2).getSum();
        return getRateOfChange(sumT1, sumT2);
    }

    public static double getRateOfChange(long l1, long l2) {
        return (l2 - l1) / l1;
    }

}
