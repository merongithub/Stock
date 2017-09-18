package com.stock.price.controller;


import com.stock.price.model.TickerResponse;
import com.stock.price.services.PriceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/stock")
public class StockController {
    private static Logger log = LogManager.getLogger(StockController.class);


    @Autowired
    private PriceService priceService;


    @RequestMapping(method = RequestMethod.GET)
    public HashMap<String, ?> getStockPrices(@RequestParam(value = "ticker") String ticker,
                                             @RequestParam(value = "startDate") String startDate,
                                             @RequestParam(value = "endDate") String endDate,
                                             @RequestParam(value = "opt", required = false) String opt) {

        if (opt != null && opt.equals("max-daily-profit"))
            return priceService.getMaximumDayProfits(ticker, startDate, endDate);
        return priceService.getMonthlyAverageOpenAndClosePrice(ticker, startDate, endDate);
    }

}
