package exercise;

import exercise.daytimes.Daytime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {
    @Autowired
    private Daytime daytime;
    @Autowired
    private Meal meal;

    @GetMapping(path = "/daytime")
    public String haveANiceMeal() {
        System.out.println("It is " + daytime.getName() + " now. Enjoy your " + meal.getMealForDaytime(daytime.getName()));
        return "It is " + daytime.getName() + " now. Enjoy your " + meal.getMealForDaytime(daytime.getName());
    }
}
// END
