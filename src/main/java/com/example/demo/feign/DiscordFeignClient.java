package com.example.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.http.HttpResponse;
import java.util.HashMap;

@FeignClient(name="Notification", url="http://backend_notification:8091")
public interface DiscordFeignClient {

    //https://discord.com/api/webhooks/1373541693508423710/5Ju3dvAmhhpk1jYK9KpOtq0l-wvoCPq8rHyifm_CGho3Y3PKrpxpkVTueJQC4mtlaW4l
    @PostMapping(value = "/notification", produces = {"application/json"})
    @ResponseBody
    HttpResponse sendDiscordMessage(@RequestBody HashMap<String,Object> message);
}
