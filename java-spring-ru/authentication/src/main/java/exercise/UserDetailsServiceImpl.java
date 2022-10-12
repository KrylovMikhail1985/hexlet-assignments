package exercise;

import exercise.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Список полномочий, которые будут предоставлены пользователю после аутентификации
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("user"));

        // BEGIN
        String password = repository.findByUsername(username).orElseThrow(()-> new UserNotFoundException("my user not found")).getPassword();
        UserDetails user = User.withUsername(username)
                .password(password)
                .authorities(authorities)
//                .roles("user")
                .build();

        return user;
        // END
    }
}
