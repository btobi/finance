package de.tum.services;

import de.tum.models.Stock;
import de.tum.models.StockValue;
import de.tum.repositories.StockRepository;
import de.tum.repositories.StockValueRepository;
import de.tum.utils.Formatters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Service
@Slf4j
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockValueRepository stockValueRepository;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private static NumberFormat numberFormat = NumberFormat.getInstance(Locale.GERMAN);

    public void handleStock(String type, String isin, String name, String date, String value) {
        Stock stock = stockRepository.findByIsin(isin);
        if (stock == null) {
            stock = Stock.builder().isin(isin).type(type).name(name).build();
            log.info("Save new StockInfo {}", stock.toString());
            stockRepository.save(stock);
        } else {
            log.debug("StockInfo already exists in db: {}", stock.toString());
        }

        Date parsedDate = null;

        try {
            parsedDate = Formatters.dateFormatLong.parse(date);
            log.debug("{} {}", parsedDate, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StockValue stockValue = stockValueRepository.findByStockIsinAndDate(isin, parsedDate);
        if (stockValue == null) {
            Long parsedValue = 0L;
            try {
                parsedValue = Math.round(numberFormat.parse(value).doubleValue() * 1000);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            stockValue = StockValue.builder().stock(stock).date(parsedDate).value(parsedValue).build();
            log.info("Save new StockInfo Value {}", stockValue.toString());
            stockValueRepository.save(stockValue);
        } else {
            log.debug("StockValue already exists in db: {}", stockValue.toString());
        }


    }
}
