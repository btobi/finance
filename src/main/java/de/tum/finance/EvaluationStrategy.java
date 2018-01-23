package de.tum.finance;

import java.time.LocalDate;
import java.util.Map;

public abstract class EvaluationStrategy {

    public abstract void evaluate(Map<LocalDate, Map<String, Long>> timeMap, Map<String, Map<LocalDate, Long>> stockMap, Map<String, StockInfo> stocks);

}
