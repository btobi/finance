package de.tum.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
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

    public String toString() {
        return Stream.of("[STOCK]: ", isin, type, name).collect(Collectors.joining(" "));
    }

}
