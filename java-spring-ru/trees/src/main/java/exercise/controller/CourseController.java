package exercise.controller;

import exercise.model.Course;
import exercise.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping(path = "")
    public Iterable<Course> getCorses() {
        return courseRepository.findAll();
    }

    @GetMapping(path = "/{id}")
    public Course getCourse(@PathVariable long id) {
        return courseRepository.findById(id);
    }

    // BEGIN
    private final String DOT_AS_STRING = "\\.";
    @GetMapping(path = "/{id}/previous/")
    public List<Course> showAllCoursesBeforeThis(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("There is not course with id= " + id));
        String[] str = course.getPath().split(DOT_AS_STRING);
        List<Long> listIdOfPreviousCourses = Arrays.stream(course.getPath().split(DOT_AS_STRING))
                .map((s) ->  Long.parseLong(s))
                .collect(Collectors.toList());
        System.out.println(listIdOfPreviousCourses);
        return (List<Course>) courseRepository.findAllById(listIdOfPreviousCourses);
    }
    // END
}
