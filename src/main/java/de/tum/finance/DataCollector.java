package de.tum.finance;

import de.tum.models.StockValue;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresProblem;

import java.time.LocalDate;
import java.util.*;

public class DataCollector {

    private Map<LocalDate, Map<String, Long>> timeMap = new HashMap<>();
    private Map<String, Map<LocalDate, Long>> stockMap = new HashMap<>();
    private Map<String, StockInfo> stocks = new HashMap<>();

    private List<EvaluationStrategy> strategies = Arrays.asList(
            new RelativeStrengthStrategy()
    );

    public DataCollector(List<StockValue> stockValues) {

        for (StockValue stockValue : stockValues) {

            // time map
            timeMap.putIfAbsent(stockValue.getDate(), new HashMap<>());
            timeMap.get(stockValue.getDate()).put(stockValue.getStock().getIsin(), stockValue.getValue());

            // stock map
            stockMap.putIfAbsent(stockValue.getStock().getIsin(), new HashMap<>());
            stockMap.get(stockValue.getStock().getIsin()).put(stockValue.getDate(), stockValue.getValue());

            stocks.put(stockValue.getStock().getIsin(), stockValue.getStock().toStockInfo());

            strategies.get(0).evaluate(this.timeMap, this.stockMap, this.stocks);

            strategies.forEach(s -> s.evaluate(this.timeMap, this.stockMap, this.stocks));

        }


    }

}
