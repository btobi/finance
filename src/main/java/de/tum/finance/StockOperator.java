package de.tum.finance;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StockOperator {


    private Map<LocalDate, Map<String, Long>> timeMap;

    private StockOperator(Map<LocalDate, Map<String, Long>> timeMap) {
        this.timeMap = timeMap;
    }

    public static StockOperator init(Map<LocalDate, Map<String, Long>> timeMap) {
        return new StockOperator(timeMap);
    }

    public StockOperator inInterval(LocalDate d1, LocalDate d2) {
        timeMap = timeMap.entrySet().stream().filter(e -> e.getKey().isAfter(d1)).filter(e -> e.getKey().isBefore(d2)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return this;
    }

    public StockOperator atDate(LocalDate date) {
        timeMap = timeMap.entrySet().stream().filter(e -> e.getKey().equals(date)).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return this;
    }

    public StockOperator filterValues(List<String> stocks) {
        timeMap = timeMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().entrySet().stream().filter(v -> stocks.contains(v.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
        return this;
    }

    public long getSum() {
        return timeMap.values().stream().map(Map::values).flatMap(Collection::stream).reduce((l1, l2) -> l1 + l2).orElse(0L);
    }

}
