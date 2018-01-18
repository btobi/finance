package de.tum.data;

import de.tum.models.Stock;
import de.tum.models.StockValue;
import de.tum.utils.DateUtils;
import de.tum.utils.Formatters;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.tum.data.RangeType.*;
import static de.tum.utils.Formatters.dateFormat;


@Slf4j
public class StockStat {

    private static final int MOMENTUM_WEEKS_MINOR = 1;
    private static final int RELATIVE_STRENGHT_WEEKS_MAJOR = 7;

    @Getter
    private final Stock stock;
    private final List<StockValue> stockValues;

    public StockStat(Stock stock) {
        this.stock = stock;
        this.stockValues = stock.getStockValues();
    }

    public HashMap<String, Object> getDefaultValues() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("stock", stock.toDto());
        map.put("values", Arrays.asList(
                getValue(SIX_MONTHS),
                getValue(THREE_MONTHS),
                getValue(ONE_MONTH),
                getValue(ONE_WEEK),
                getValue(YESTERDAY)
        ));
        map.put("momentum", getMomentum(MOMENTUM_WEEKS_MINOR, RELATIVE_STRENGHT_WEEKS_MAJOR));
        return map;
    }

    public StockValueRange getValue(RangeType rangeType) {
        StockValue value = getValueByRange(rangeType);
        StockValue currentvalue = stockValues.get(0);

        Double absoluteDiff;
        Double relativeDiff;

        if (value == null || currentvalue == null) {
            absoluteDiff = 0d;
            relativeDiff = 0d;
        } else {
            absoluteDiff = currentvalue.getRealValue() - value.getRealValue();
            relativeDiff = absoluteDiff / value.getRealValue() * 100;
        }


        return StockValueRange.builder()
                              .rangeType(rangeType)
                              .date(value != null ? value.getDate() : null)
                              .absoluteDiff(Formatters.numberFormat.format(absoluteDiff))
                              .relativeDiff(Formatters.numberFormat.format(relativeDiff))
                              .relativeDiffRaw(relativeDiff)
                              .build();
    }

    private StockValue getValueByRange(RangeType rangeType) {
        LocalDate now = LocalDate.now();
        switch (rangeType) {
            case SIX_MONTHS:
                now = now.minusMonths(6);
                break;
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

    private double getMomentum(int weeksMinor, int weeksMajor) {
        LocalDate minor = LocalDate.now().minusWeeks(weeksMinor);
        LocalDate major = LocalDate.now().minusWeeks(weeksMajor);
        LocalDate now = LocalDate.now();

        double minorAvg = getAverage(minor, now);
        double majorAvg = getAverage(major, now);

        log.debug("{}, {}, {} ", stock.getName(), minorAvg, majorAvg);

        return minorAvg / majorAvg;
    }

    private double getAverage(LocalDate from, LocalDate to) {
        return stockValues.stream()
                          .filter(p -> p.getDate().after(DateUtils.localDatetoDate(from)))
                          .filter(p -> p.getDate().before(DateUtils.localDatetoDate(to)))
                          .mapToDouble(StockValue::getRealValue)
                          .average()
                          .orElse(99999999d);
    }

    public String toString() {
        StockValueRange m3 = getValue(THREE_MONTHS);
        StockValueRange w1 = getValue(RangeType.ONE_WEEK);
        StockValueRange yesterday = getValue(RangeType.YESTERDAY);

        return Stream.of(stock.getName(), dateFormat.format(m3.date), m3.relativeDiff, dateFormat.format(w1.date), w1.relativeDiff, yesterday.relativeDiff)
                     .collect(Collectors.joining(" "));
    }
}
