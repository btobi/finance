package de.tum.finance;

import de.tum.models.Stock;
import de.tum.models.StockValue;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataCollector {

    private Map<LocalDate, Map<String, Long>> timeMap = new HashMap<>();
    private Map<String, Map<LocalDate, Long>> stockMap = new HashMap<>();

    public DataCollector(List<StockValue> stockValues) {

        for (StockValue stockValue : stockValues) {

            // time map
            timeMap.putIfAbsent(stockValue.getDate(), new HashMap<>());
            timeMap.get(stockValue.getDate()).put(stockValue.getStock().getIsin(), stockValue.getValue());

            // stock map
            stockMap.putIfAbsent(stockValue.getStock().getIsin(), new HashMap<>());
            stockMap.get(stockValue.getStock().getIsin()).put(stockValue.getDate(), stockValue.getValue());



        }


    }

}
