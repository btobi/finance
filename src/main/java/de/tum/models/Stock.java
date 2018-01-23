package de.tum.models;


import de.tum.finance.StockInfo;
import de.tum.utils.StockDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@Table(name = "stocks")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    private String isin;
    private String type;
    private String name;

    @OneToMany(mappedBy = "stock")
    @OrderBy(value = "date DESC")
    private List<StockValue> stockValues;

    public String toString() {
        return Stream.of("[STOCK]: ", isin, type, name).collect(Collectors.joining(" "));
    }

    public StockDto toDto() {
        return StockDto.builder().isin(isin).type(type).name(name).build();
    }

    public StockInfo toStockInfo() {
        return StockInfo.builder().isin(this.isin).type(this.type).build();
    }

}
