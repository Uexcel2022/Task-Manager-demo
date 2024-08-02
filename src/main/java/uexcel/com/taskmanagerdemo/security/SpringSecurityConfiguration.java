package uexcel.com.taskmanagerdemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.function.Function;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {

    Function<String,String> passwordEncoder =
            password -> getBCryptPasswordEncoder().encode(password);

    @Bean
    public InMemoryUserDetailsManager getInMemoryUserDetailsManager() {

//        UserDetails userDetails = User
//                .builder().passwordEncoder(passwordEncoder)
//                .username("uexcel")
//                .password("1234")
//                .roles("USER","ADMIN")
//                .build();

        return new InMemoryUserDetailsManager(

                buildUser("uexcel","1234",new String[]{"ADMIN","USER"}),
                buildUser("udoka","12345", new String[]{"USER"})
        );
    }


    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private  UserDetails buildUser(String username, String password, String[] role) {
        return User
        .builder().passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles(role)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                auth->auth.anyRequest().authenticated());
                http.formLogin(withDefaults());
         http.csrf(AbstractHttpConfigurer::disable);
         http.headers(headers->headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

         return http.build();
    }
}
