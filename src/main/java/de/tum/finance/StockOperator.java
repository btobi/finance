package de.tum.finance;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
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
        timeMap = timeMap.entrySet()
                         .stream()
                         .filter(e -> e.getKey().isAfter(d1))
                         .filter(e -> e.getKey().isBefore(d2))
                         .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return this;
    }

    public StockOperator atDate(LocalDate date) {
        timeMap = timeMap.entrySet()
                         .stream()
                         .filter(e -> e.getKey().equals(date))
                         .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return this;
    }

    public StockOperator filterValues(Set<String> stocks) {
        timeMap = timeMap.entrySet()
                         .stream()
                         .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()
                                                                            .entrySet()
                                                                            .stream()
                                                                            .filter(v -> stocks.contains(v.getKey()))
                                                                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
        return this;
    }

    public long getSum() {
        return timeMap.values()
                      .stream()
                      .map(Map::values)
                      .flatMap(Collection::stream)
                      .reduce((l1, l2) -> l1 + l2)
                      .orElse(0L);
    }

    public Map<LocalDate, Long> getSumByDay() {
        return timeMap.entrySet()
               .stream()
               .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()
                                                                  .values()
                                                                  .stream()
                                                                  .reduce((l1, l2) -> l1 + l2)
                                                                  .orElse(0L)));
    }

    public Map<String, Double> getStandardDeviationByStock() {
        return getStockMap().entrySet()
                            .stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, e -> this.sd(e.getValue().values())));
    }

    public Double getStandardDeviationByPortfolio() {
        return this.sd(getSumByDay().values());
    }

    private Map<String, Map<LocalDate, Long>> getStockMap() {
        Map<String, Map<LocalDate, Long>> stockMap = new HashMap<>();
        for (Map.Entry<LocalDate, Map<String, Long>> entry : timeMap.entrySet()) {
            for (Map.Entry<String, Long> value : entry.getValue().entrySet()) {
                stockMap.putIfAbsent(value.getKey(), new HashMap<>());
                stockMap.get(value.getKey()).put(entry.getKey(), value.getValue());
            }

        }
        return stockMap;
    }

    private double sd(Collection<Long> values) {
        double[] v = values.stream().mapToDouble(Long::doubleValue).toArray();
        double sd = new StandardDeviation().evaluate(v);
        double mean = new Mean().evaluate(v);
        return sd / mean;
    }

}
