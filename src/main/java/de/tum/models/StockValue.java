package de.tum.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Entity
@Table(name = "stock_history")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockValue {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "stock_isin")
    private Stock stock;

    private LocalDate date;

    private Long value; // in 1 / 1000 Euro

    public String toString() {
        return Stream.of("[StockValue]: ", stock.toString(), date.toString(), value / 1000 + "").collect(Collectors.joining(" "));
    }

    public Double getRealValue() {
        return Math.round(value.doubleValue() / 100d) / 10d;
    }

}
