package de.tum.repositories;

import de.tum.models.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Integer> {

    Stock findByIsin(String isin);

}
