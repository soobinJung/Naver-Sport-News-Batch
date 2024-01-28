package com.naver.sport.NaverBatchToyProject.process;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class NaverSportNewsTitleWrite implements ItemWriter<NaverSportNewsTitleDto> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void write(Chunk<? extends NaverSportNewsTitleDto> chunk) {
        for(NaverSportNewsTitleDto naverSportNewsTitleDto : chunk.getItems()) {
            System.out.println("저장 : " + naverSportNewsTitleDto.getTitle());
            jdbcTemplate.update("INSERT INTO NAVER_SPORT_NEWS_TITLE (title) VALUES(?)", naverSportNewsTitleDto.getTitle());
        }
    }
}