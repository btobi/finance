package de.tum.data;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public class StockValueRange {

    public RangeType rangeType;
    public String absoluteDiff;
    public String relativeDiff;
    public Double relativeDiffRaw;
    public LocalDate date;

}
