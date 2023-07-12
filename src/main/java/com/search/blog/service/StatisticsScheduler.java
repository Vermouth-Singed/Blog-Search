package com.search.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class StatisticsScheduler {
    @Autowired
    StatisticsService statisticsService;

    @Scheduled(fixedDelay = 10000, initialDelay = 2000)
    public void statisticsScheduler() {
        statisticsService.stackStatisticsData();
    }
}
