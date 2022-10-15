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
    private ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/cities/{id}")
    public Map<String, Object> showCityAndWeather(@PathVariable(name = "id") long id) throws JsonProcessingException {
        String nameOfTheCity = cityRepository.findById(id).orElseThrow(
                ()-> new CityNotFoundException("City not found"))
                .getName();
        Map<String, Object> result = weatherOfTheCity(cityRepository.findById(id).orElseThrow());
        result.replace("name", nameOfTheCity);

        return result;
    }
    @GetMapping("/search")
    public List<Map<String, String>> showAllCitiesWithTemperatures(@RequestParam(value = "name", required = false) String str) throws JsonProcessingException {
        List<Map<String, String>> result = new ArrayList<>();
        List<City> listOfCities;
        if (str == null) {
            listOfCities = cityRepository.findAllByOrderByName();
        } else {
            listOfCities = cityRepository.findByNameStartingWithIgnoreCase(str);
        }
        for (City city: listOfCities) {
            Map<String, Object> weather = weatherOfTheCity(city);
            String temperature = weather.get("temperature").toString();
            Map<String, String> map = new HashMap<>();
            map.put("temperature", temperature);
            map.put("name", city.getName());
            result.add(map);
        }
        return result;
    }
    private Map<String, Object> weatherOfTheCity(City city) throws JsonProcessingException {
        String weatherOfTheCity = weatherService.showWeatherInTheCity(city);
        return objectMapper.readValue(weatherOfTheCity, new TypeReference<>() {
        });
    }
    // END
}

