package exercise.controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exercise.CityNotFoundException;
import exercise.model.City;
import exercise.repository.CityRepository;
import exercise.service.WeatherService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class CityController {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private WeatherService weatherService;

    // BEGIN
    @GetMapping("/cities/{id}")
    public Map<String, String> showCityAndWeather(@PathVariable(name = "id") long id) throws JsonProcessingException {
        String nameOfTheCity = cityRepository.findById(id).orElseThrow(
                ()-> new CityNotFoundException("City not found"))
                .getName();
        Map<String, String> result = weatherService.returnWeatherInTheCity(cityRepository.findById(id).orElseThrow());
        result.replace("name", nameOfTheCity);
        return result;
    }
    @GetMapping("/search")
    public List<Map<String, String>> showAllCitiesWithTemperatures(@RequestParam(value = "name", required = false) String str) throws JsonProcessingException {

        List<City> listOfCities;
        if (str == null) {
            listOfCities = cityRepository.findAllByOrderByName();
        } else {
            listOfCities = cityRepository.findByNameStartingWithIgnoreCase(str);
        }

        List<Map<String, String>> result;
//        for (City city: listOfCities) {
//            Map<String, String> weather = weatherService.returnWeatherInTheCity(city);
//            String temperature = weather.get("temperature");
//
//            Map<String, String> map = new HashMap<>();
//            map.put("temperature", temperature);
//            map.put("name", city.getName());
//            result.add(map);
//        }
        result = listOfCities.stream()
                .map(city -> {
                    Map<String, String> weather = weatherService.returnWeatherInTheCity(city);
                    return Map.of(
                            "temperature", weather.get("temperature"),
                            "name", city.getName()
                    );
                }).toList();
        return result;
    }
    // END
}

