package exercise;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import exercise.daytimes.Daytime;
import exercise.daytimes.Morning;
import exercise.daytimes.Day;
import exercise.daytimes.Evening;
import exercise.daytimes.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// BEGIN
@Configuration
public class MyApplicationConfig {
//    @Bean
//    public Meal meal() {
//        return new Meal();
//    }
    @Bean
    public Daytime timeOfADay() {
        LocalTime time = LocalTime.now();
        LocalTime time1 = LocalTime.of(6, 0);
        LocalTime time2 = LocalTime.of(12, 0);
        LocalTime time3 = LocalTime.of(18, 0);
        LocalTime time4 = LocalTime.of(23, 0);

        Daytime result;
        if (time.isAfter(time1) && time.isBefore(time2)) {
            result = new Morning();
        } else  if (time.isAfter(time2) && time.isBefore(time3)) {
            result = new Day();
        } else  if (time.isAfter(time3) && time.isBefore(time4)) {
            result = new Evening();
        } else  {
            result = new Night();
        }
        return result;
    }
}
// END
