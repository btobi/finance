package de.tum.data;

import lombok.Builder;

import java.util.Date;

@Builder
public class StockValueRange {

    public RangeType rangeType;
    public String absoluteDiff;
    public String relativeDiff;
    public Double relativeDiffRaw;
    public Date date;

}
