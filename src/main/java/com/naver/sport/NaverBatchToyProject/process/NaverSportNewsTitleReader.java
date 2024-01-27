package com.naver.sport.NaverBatchToyProject.process;

import org.springframework.batch.item.ItemStreamReader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NaverSportNewsTitleReader implements ItemStreamReader<NaverSportNewsTitleDto> {

    private List<NaverSportNewsTitleDto> newsTitles;
    private Iterator<NaverSportNewsTitleDto> dataIterator;

    private final String API_URL = "https://sports.naver.com/news?oid=001&aid=";
    private final String[] PARAMETER = {
        "0014471291"
        , "0014471292"
        , "0014471293"
        , "0014471294"
        , "0014471295"
        , "0014471296"
    };
    private final String[] TITLE_TAG = {"<title>", "</title>"};

    public NaverSportNewsTitleReader() {
        this.newsTitles = fetchApiData();
        this.dataIterator = newsTitles.iterator();
    }

    private List<NaverSportNewsTitleDto> fetchApiData() {

        return Arrays.stream(PARAMETER).map( param -> {
            try{
                URL url = new URL(API_URL + param);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String result = response.toString();
                int startIdx = result.indexOf(TITLE_TAG[0]);
                int endIdx = result.indexOf(TITLE_TAG[1]);

                String title = result.substring(startIdx + 7, endIdx);
                return NaverSportNewsTitleDto.builder().title(title).build();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    public NaverSportNewsTitleDto read() {
        if (dataIterator.hasNext()) {
            return dataIterator.next();
        } else {
            return null;
        }
    }
}