package com.stock.price.services;


import com.stock.price.model.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class PriceService {

    private QuandliResponse quandliResponse;
    private HashMap<String, TickerResponse> allValues;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat oldformat = new SimpleDateFormat("yyyy-MM-dd");
    private static Logger log = LogManager.getLogger(PriceService.class);


    @Autowired
    private QuandlClient quandlClient;

    public HashMap<String, TickerResponse> getMonthlyAverageOpenAndClosePrice(@NotNull String ticker,
                                                                              @NotNull String startDate,
                                                                              @NotNull String endDate) {
        try {
            DataTable dataTable = getDataTable(ticker, startDate, endDate);
            allValues = calculateMonthlyAverageOpenAndClosePrice(dataTable);
        } catch (Exception e) {
            log.error("Unable to create tickerRespons");
        }
        return allValues;
    }

    private HashMap<String, TickerResponse> calculateMonthlyAverageOpenAndClosePrice(DataTable dataTable) {
        TickerResponse tickerResponse = new TickerResponse();
        HashMap<String, TickerResponse> tickerResponseHashMap = new HashMap<String, TickerResponse>();
        HashMap<String, PriceRequest> priceRequest;
        try {
            HashMap<String, HashMap<String, PriceRequest>> datePriceMap = getTickerDateAndPriceMap(dataTable);
            log.info("DataPiceMap size " + datePriceMap.size());
            if (datePriceMap == null || datePriceMap.size() == 0)
                throw new Exception("Empty dataPirceMap");
            for (String tickerName : datePriceMap.keySet()) {
                priceRequest = datePriceMap.get(tickerName);
                for (String yearMonthDate : priceRequest.keySet()) {
                    log.info("Calculating for yearMonthDate " + yearMonthDate);
                    TickerValues tickerValues = new TickerValues();
                    tickerValues.setMonth(yearMonthDate);
                    tickerValues.setAverage_close(priceRequest.get(yearMonthDate).getAvarageClosePriceSum());
                    tickerValues.setAverage_open(priceRequest.get(yearMonthDate).getAvarageOpenPriceSum());
                    tickerResponse.addVlues(tickerValues);
                }
                tickerResponseHashMap.put(tickerName, tickerResponse);
            }
        } catch (Exception e) {
            log.info("PriceServiceException " + e.getMessage());
        }
        return tickerResponseHashMap;
    }

    private HashMap<String, HashMap<String, PriceRequest>> getTickerDateAndPriceMap(DataTable dataTable) {
        HashMap<String, HashMap<String, PriceRequest>> tickerdatePriceMap = new HashMap<String, HashMap<String, PriceRequest>>();
        HashMap<String, PriceRequest> datePriceMap = null;
        log.info("Size of dataTable is: " + dataTable.getData().size()); //size of the List
        try {
            for (int i = 0; i < dataTable.getData().size(); i++) {
                List<String> row = dataTable.getData().get(i);
                String ticker = row.get(0);
                Date readDate = oldformat.parse(row.get(1));
                String yearAndMonth = format.format(readDate);
                if (!tickerdatePriceMap.containsKey(ticker)) { //firstTime ticker found
                    PriceRequest priceRequest = new PriceRequest();
                    priceRequest.addOpenPrice(Double.parseDouble(row.get(2)));//OpenPrice
                    priceRequest.addClosePrice(Double.parseDouble(row.get(5))); //ClosePrice
                    datePriceMap = new HashMap<String, PriceRequest>();
                    datePriceMap.put(yearAndMonth, priceRequest);
                    tickerdatePriceMap.put(ticker, datePriceMap);
                } else {
                    datePriceMap = tickerdatePriceMap.get(ticker);
                    if (!datePriceMap.containsKey(yearAndMonth)) {
                        PriceRequest priceRequest = new PriceRequest();
                        priceRequest.addOpenPrice(Double.parseDouble(row.get(2)));//OpenPrice
                        priceRequest.addClosePrice(Double.parseDouble(row.get(5))); //ClosePrice
                        datePriceMap.put(yearAndMonth, priceRequest);

                    } else {
                        datePriceMap.get(yearAndMonth).addOpenPrice(Double.parseDouble(row.get(2)));
                        datePriceMap.get(yearAndMonth).addClosePrice(Double.parseDouble(row.get(5)));
                    }
                }
            }
        } catch (ParseException pe) {
            log.info("Parsing Exception " + pe.getMessage());
        } catch (Exception e) {
            log.info("Exception creating dataPriceMap " + e.getMessage());
        }

        return tickerdatePriceMap;
    }

    public HashMap<String, HashMap<String, String>> getMaximumDayProfits(@NotNull String tickerName,
                                                                         @NotNull String startDate,
                                                                         @NotNull String endDate) {
        DataTable dataTable = getDataTable(tickerName, startDate, endDate);
        HashMap<String, HashMap<String, String>> highProfitMap = new HashMap<String, HashMap<String, String>>();
        Double maxValue = Double.MIN_VALUE;
        List<String> maxRow = null;

        try {
            String ticker = dataTable.getData().get(0).get(0); //init ticker
            for (int i = 0; i < dataTable.getData().size(); i++) {
                List<String> row = dataTable.getData().get(i);
                if (ticker.equals(row.get(0))) {
                    Double highValue = Double.parseDouble(row.get(3));
                    Double lowValue = Double.parseDouble(row.get(4));
                    Double difference = highValue - lowValue;
                    if (difference > maxValue) {
                        maxRow = row;
                        maxValue = difference;
                    }
                } else {
                    HashMap<String, String> maxRowMap = new HashMap<String, String>();
                    maxRowMap.put("Date", maxRow.get(1));
                    maxRowMap.put("MaximumProfit", maxValue.toString());
                    highProfitMap.put(ticker, maxRowMap);
                    ticker = row.get(0);// re-assign ticker
                    maxValue = Double.parseDouble(row.get(3)) - Double.parseDouble(row.get(4));
                    maxRow = row;

                }

            }
            HashMap<String, String> maxRowMap = new HashMap<String, String>();
            maxRowMap.put("date", maxRow.get(1));
            maxRowMap.put("MaximumProfilet", maxValue.toString());
            highProfitMap.put(ticker, maxRowMap);

        } catch (Exception e) {
            log.info("Exception creating highProfitMap" + e.getMessage());
        }

        return highProfitMap;
    }

    private DataTable getDataTable(String ticker, String startDate, String endDate) {
        DataTable dataTable = null;
        try {
            quandliResponse = quandlClient.getStockPrice(ticker, startDate, endDate);
            dataTable = quandliResponse.getDatatable();
            if (dataTable == null)
                throw new Exception("Null value of dataTable from QuandlResponse :" + dataTable.toString());
            return dataTable;
        } catch (Exception e) {
            log.error("Empty dataTable");
        }
        return dataTable;
    }


}

