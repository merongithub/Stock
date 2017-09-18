package com.stock.price.services;


import com.stock.price.model.QuandliResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static java.lang.String.format;

@Service
public class QuandlClient {
    private final static String QUAND_ENDPOINT_TEMPLATE = "date.gte=%s&date.lt=%s&ticker=%s&api_key=%s";
    @Value("${quandlUrl.endpoint}")
    private String quandlUrl;
    @Value("${quadlUrl.apiKey}")
    private String quandlApiKey;

    private static Logger log = LogManager.getLogger(QuandlClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public QuandliResponse getStockPrice(String ticker, String startDate, String endDate) {
        String uri = quandlUrl + format(QUAND_ENDPOINT_TEMPLATE, startDate, endDate, ticker, quandlApiKey);
        URI quandURI = URI.create(uri);
        try {
            log.info("QuandleClient requestUri : " + uri);
            ResponseEntity<QuandliResponse> response = restTemplate.getForEntity(quandURI, QuandliResponse.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException(format("HTTP Status : %s. Failed to  make request for %s", response.getStatusCode(), ticker));
            }
            QuandliResponse quandliResponse = response.getBody();
            log.info("QuandlClient Response " + quandliResponse.toString());
            return quandliResponse;
        } catch (RestClientException restEx) {
            throw new RuntimeException(format("Failed to make request for %s", ticker), restEx);
        }

    }
}
