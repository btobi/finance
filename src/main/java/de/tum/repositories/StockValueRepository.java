package de.tum.repositories;

import de.tum.models.StockValue;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

public interface StockValueRepository extends CrudRepository<StockValue, Integer> {

    StockValue findByStockIsinAndDate(String stockIsin, Date timestamp);

}
