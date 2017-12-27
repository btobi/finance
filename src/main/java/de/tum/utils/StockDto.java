package de.tum.utils;

import lombok.Builder;

@Builder
public class StockDto {

    public String isin;
    public String type;
    public String name;

}
