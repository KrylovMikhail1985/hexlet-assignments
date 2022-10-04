package exercise.controller;

import exercise.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import javax.management.Query;
import java.util.*;

@RestController
@RequestMapping("/people")
public class PeopleController {
    @Autowired
    JdbcTemplate jdbc;

    @PostMapping(path = "")
    public void createPerson(@RequestBody Map<String, Object> person) {
        String query = "INSERT INTO person (first_name, last_name) VALUES (?, ?)";
        jdbc.update(query, person.get("first_name"), person.get("last_name"));
    }

    // BEGIN
    @GetMapping(path = "")
    public List<Person> showAllPeople() {
        List<Person> result = new ArrayList<>();
        String sql = "SELECT * FROM person";
        List<Map<String, Object>> allPerson= jdbc.queryForList(sql);
        for (Map map: allPerson) {
            result.add(getPersonFromMap(map));
        }
        return result;
    }


    @GetMapping(path = "{id}")
    public Person showCurrentPerson(@PathVariable int id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);
        String sql = "SELECT * FROM person WHERE ID =" + id;

        List<Map<String, Object>> list = jdbc.queryForList(sql);
        Map<String, Object> map = null;
        Person person = new Person();
        if (!list.isEmpty()) {
            map = list.get(0);
            person = getPersonFromMap(map);
        }
        assert map != null;
        return person;
    }
    private Person getPersonFromMap(Map<String, Object> map) {
        Person person = new Person();
        person.setId(map.get("ID").toString());
        person.setFirst_name(map.get("FIRST_NAME").toString());
        person.setLast_name(map.get("LAST_NAME").toString());
        return person;
    }

    // END
}
