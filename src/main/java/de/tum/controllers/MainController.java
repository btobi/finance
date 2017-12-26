package de.tum.controllers;

import de.tum.data.StockStat;
import de.tum.models.Stock;
import de.tum.repositories.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MainController {

    @Autowired
    private StockRepository stockRepository;

    @RequestMapping("")
    public String helloWorld() {

        List<Stock> stocks = stockRepository.findAll();

        List<StockStat> stats = stocks.stream().map(StockStat::new).collect(Collectors.toList());

        stats.forEach(s -> log.debug(s.toString()));

        return "hello";
    }

}
