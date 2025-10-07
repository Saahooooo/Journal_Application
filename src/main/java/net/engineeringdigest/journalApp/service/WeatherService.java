package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.AppCache;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.constants.Placeholders;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static net.engineeringdigest.journalApp.entity.User.*;

@Service
public class WeatherService {

    @Autowired
    private RedisService redisService;

    @Value("${weather.api.key}")
    private  String apiKey;
//    private static final String API="https://api.weatherstack.com/current?access_key=API_KEY&query=CITY\n";

//    the restTemplate processes https and gives us response
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if (weatherResponse!=null){
            return weatherResponse;
        }else {

            String fianlAPI=appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,apiKey);

            ResponseEntity<WeatherResponse> response = restTemplate.exchange(fianlAPI, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if (body!=null){
                redisService.set("weather_of_"+city,body,300l);
            }
            return body;
        }

//        HttpHeaders httpHeaders=new HttpHeaders();
//        httpHeaders.set("key","value");
//        User user=User.builder().userName("Ram").password("Ram").build();
//        HttpEntity<User>httpEntity=new HttpEntity<>(user,httpHeaders);

    }
}
