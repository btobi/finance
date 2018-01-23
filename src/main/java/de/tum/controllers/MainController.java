package de.tum.controllers;

import de.tum.repositories.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/")
    public String helloWorld() {

//        List<Stock> stocks = stockRepository.findAll();
//
//        List<StockStat> stats = stocks.stream().map(StockStat::new).collect(Collectors.toList());
//
//        stats.forEach(s -> log.debug(s.toString()));
//
        return "index";
    }

}
