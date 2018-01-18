package de.tum.controllers;

import de.tum.data.StockStat;
import de.tum.models.Stock;
import de.tum.repositories.StockRepository;
import de.tum.utils.StockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BasicApiController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/stats/complete")
    public List<HashMap<String, Object>> getStats() {
        List<Stock> stocks = stockRepository.findAll();

        List<HashMap<String, Object>> stockMap = stocks.stream()
                                                       .map(StockStat::new)
                                                       .map(StockStat::getDefaultValues)
                                                       .sorted(Comparator.comparing(s -> ((StockDto) s.get("stock")).isin))
                                                       .collect(Collectors.toList());

        return stockMap;

    }

}
