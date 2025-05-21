package com.example.demo.service;

import com.example.demo.feign.DiscordFeignClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ExpenditureReportingService {
    @Autowired
    DiscordFeignClient discordFeignClient;
    @Autowired
    ExpenditureService expenditureService;

    @Scheduled(cron = "0 * * * * MON-FRI")
    private void sendReportToDiscord(){
        List<Map<String,Object>> myExpenditure = expenditureService.calTotalExpenditureByReceiver("testing");
        HashMap<String, Object> discordMessage = new HashMap<String, Object>();
        discordMessage.put("content", ""+myExpenditure.get(0).get("totalexpenditure"));
        discordFeignClient.sendDiscordMessage(discordMessage);
    }

}
