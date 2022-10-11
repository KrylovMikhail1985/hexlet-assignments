package exercise.controller;
import exercise.model.User;
import exercise.model.QUser;
import exercise.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Зависимости для самостоятельной работы
 import org.springframework.data.querydsl.binding.QuerydslPredicate;
 import com.querydsl.core.types.Predicate;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserRepository userRepository;

    // BEGIN
    @GetMapping("")
    public Iterable<User> getAllUserByFirstAndLastName(
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName
            ) {
//        List<Predicate> predicates = new ArrayList<>();
//        if(firstName != null) {
//            predicates.add(QUser.user.firstName.toLowerCase().containsIgnoreCase(firstName));
//        }
//        if (lastName != null) {
//            predicates.add(QUser.user.lastName.toLowerCase().containsIgnoreCase(lastName));
//        }
        if(firstName != null && lastName != null) {
            return userRepository.findAll(
                    QUser.user.firstName.toLowerCase().containsIgnoreCase(firstName)
                            .and(QUser.user.lastName.toLowerCase().containsIgnoreCase(lastName))
            );
        }
        if (firstName == null && lastName == null) {
            return userRepository.findAll();
        }
        if (firstName != null) {
            return userRepository.findAll(QUser.user.firstName.toLowerCase().containsIgnoreCase(firstName));
        }
        return userRepository.findAll(QUser.user.lastName.toLowerCase().containsIgnoreCase(lastName));
    }
    // END
}

