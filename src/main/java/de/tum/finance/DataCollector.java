package de.tum.finance;

import de.tum.models.StockValue;
import lombok.Getter;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataCollector {

    private Map<LocalDate, Map<String, Long>> timeMap = new HashMap<>();
    private Map<String, Map<LocalDate, Long>> stockMap = new HashMap<>();
    private Map<String, StockInfo> stocks = new HashMap<>();

    @Getter
    private RelativeStrengthStrategy relativeStrengthStrategy = new RelativeStrengthStrategy();

    public DataCollector(List<StockValue> stockValues) {

        for (StockValue stockValue : stockValues) {

            // time map
            timeMap.putIfAbsent(stockValue.getDate(), new HashMap<>());
            timeMap.get(stockValue.getDate()).put(stockValue.getStock().getIsin(), stockValue.getValue());

            // stock map
            stockMap.putIfAbsent(stockValue.getStock().getIsin(), new HashMap<>());
            stockMap.get(stockValue.getStock().getIsin()).put(stockValue.getDate(), stockValue.getValue());

            stocks.put(stockValue.getStock().getIsin(), stockValue.getStock().toStockInfo());


        }

        this.relativeStrengthStrategy.evaluate(this.timeMap, this.stockMap, this.stocks);

    }

}
