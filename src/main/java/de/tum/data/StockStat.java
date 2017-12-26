package de.tum.data;

import de.tum.models.Stock;
import de.tum.models.StockValue;
import de.tum.utils.Formatters;
import org.w3c.dom.ranges.Range;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.tum.utils.Formatters.dateFormat;

public class StockStat {

    private final Stock stock;
    private final List<StockValue> stockValues;

    public StockStat(Stock stock) {
        this.stock = stock;
        this.stockValues = stock.getStockValues();
    }

    public StockValueRange getValue(RangeType rangeType) {
        StockValue value = getValueByRange(rangeType);
        StockValue currentvalue = stockValues.get(0);

        Double absoluteDiff = currentvalue.getRealValue() - value.getRealValue();
        Double relativeDiff = absoluteDiff / value.getRealValue() * 100;

        return StockValueRange.builder()
                .date(value.getDate())
                .absoluteDiff(Formatters.numberFormat.format(absoluteDiff))
                .relativeDiff(Formatters.numberFormat.format(relativeDiff))
                .build();
    }

    private StockValue getValueByRange(RangeType rangeType) {
        LocalDate now = LocalDate.now();
        switch (rangeType) {
            case THREE_MONTHS:
                now = now.minusMonths(3);
                break;
            case ONE_MONTH:
                now = now.minusMonths(1);
                break;
            case ONE_WEEK:
                now = now.minusWeeks(1);
                break;
            case YESTERDAY:
                now = now.minusDays(1);
        }


        Date date = Date.from(now.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return stockValues.stream().filter(p -> p.getDate().before(date)).findFirst().orElse(null);

    }

    public String toString() {
        StockValueRange m3 = getValue(RangeType.THREE_MONTHS);
        StockValueRange w1 = getValue(RangeType.ONE_WEEK);
        StockValueRange yesterday = getValue(RangeType.YESTERDAY);

        return Stream.of(stock.getName(), dateFormat.format(m3.date), m3.relativeDiff, dateFormat.format(w1.date), w1.relativeDiff, yesterday.relativeDiff).collect(Collectors.joining(" "));
    }
}
