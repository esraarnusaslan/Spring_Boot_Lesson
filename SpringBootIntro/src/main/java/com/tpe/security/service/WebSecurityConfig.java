package com.tpe.security.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity//aktif hale getir demek
@EnableGlobalMethodSecurity(prePostEnabled = true)//methodlarda yetkilendirme icin
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().  //csrf: saldiri korumasidir.   disable ile bunu kapatiyoruz.
                               // cunku baska yerden restapi session yok saldiri gerceklesmez
        authorizeHttpRequests(). //http requestleri authorize et dedik
        antMatchers("/","index.html","/css/*","/images/*","/register","/login").permitAll().//hepsine izin ver diyoruz permitAll ile
        anyRequest().authenticated().and().httpBasic();//basic auth.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){//PasswordEncoder security classidir
        return new BCryptPasswordEncoder(10);//passwordun sifrelenerek alinmasini ve db ye
                                                     // kaydedilmsini saglayan cripto yontemi.
                                                     //zorluk derecesini parantez icinde veriyosun. 4-34 arasindadir
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){//yukaridaki passwordu kullanacak diye olusturduk.
                                                             // ayni zamanda user da burdan gecip db e gidiyor.
                                                            // password da burdan gecip db e gidiyor encoder ile
        DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);//user icin
        authProvider.setPasswordEncoder(passwordEncoder());//password icin
        return authProvider;
    }

    @Override//auth manager in config i
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}
