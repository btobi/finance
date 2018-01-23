package de.tum.finance;

import lombok.Getter;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RelativeStrengthStrategy extends EvaluationStrategy {

    private static final LocalDate t1 = LocalDate.of(2017, 12, 27);
    private static final LocalDate t2 = LocalDate.of(2018, 1, 22);

    @Getter
    private double portfolioReturn;

    @Getter
    private Map<String, Double> stockReturns = new HashMap<>();

    @Getter
    private Map<String, Double> relativeReturns = new HashMap<>();

    @Getter
    private Map<String, Double> standardDeviationByStock = new HashMap<>();

    @Getter
    private Double standardDeviationPortfolio;

    private Map<String, StockInfo> stockInfo;

    @Override
    public void evaluate(Map<LocalDate, Map<String, Long>> timeMap, Map<String, Map<LocalDate, Long>> stockMap, Map<String, StockInfo> stocks) {

        this.stockInfo = stocks;

        this.portfolioReturn = Utils.calculatePerformance(timeMap, t1, t2, stocks.keySet());

        for (String stock : stocks.keySet()) {
            double stockReturn = Utils.calculatePerformance(timeMap, t1, t2, stock);
//            this.stockReturns.put(stock + " " + stockInfo.get(stock).name, stockReturn);
            this.stockReturns.put(stock, stockReturn);
//            this.relativeReturns.put(stock + " " + stockInfo.get(stock).name, stockReturn / this.portfolioReturn);
            this.relativeReturns.put(stock, stockReturn / this.portfolioReturn);
        }

        StockOperator interval = StockOperator.init(timeMap).inInterval(t1, t2).filterValues(stocks.keySet());
        this.standardDeviationByStock = interval.getStandardDeviationByStock();
        this.standardDeviationPortfolio = interval.getStandardDeviationByPortfolio();

    }

    public LinkedHashMap<String, Double> getSortedRelativeReturns() {
        return this.relativeReturns.entrySet().stream().
                        sorted(Comparator.comparing(Map.Entry::getValue)).
                                            collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                                    (e1, e2) -> e1, LinkedHashMap::new));
    }

}
