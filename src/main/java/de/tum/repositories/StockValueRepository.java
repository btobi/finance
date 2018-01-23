package de.tum.repositories;

import de.tum.models.StockValue;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockValueRepository extends CrudRepository<StockValue, Integer> {

    StockValue findByStockIsinAndDate(String stockIsin, LocalDate timestamp);

    List<StockValue> findAll();

}
