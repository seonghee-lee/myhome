package com.seonghee.myhome.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;  //application.properties에서 설정한 datasource이다. // @Autowired로 스프링이 자동 연결 시켜준다.

    //JDBC Authentication: mariaDB의 user 테이블과 연동한다.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource) //DI
                .passwordEncoder(passwordEncoder()) //비밀번호 암호화
                .usersByUsernameQuery("select username,password,enabled "
                        + "from user "
                        + "where username = ?")     //인증 처리(로그인)
                .authoritiesByUsernameQuery("select u.username,r.name "
                        + "from user_role ur inner join user u on ur.user_id = u.id "
                        + "inner join role r on ur.role_id = r.id "
                        + "where u.username = ?");    //권한 처리(페이지 별 접근 권한)
    }

    //오류나서 static 키워드 넣어놨는데 이유를 잘 모르겠음
    //https://stackoverflow.com/questions/70254555/spring-boot-2-6-0-error-creating-bean-with-name-websecurityconfig
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/account/register","/css/**").permitAll()  //permitAll(): 누구나 접근 가능: 로그인 없이도.
                    .anyRequest().authenticated()   //위 url을 제외한 모든 요청은 반드시 로그인을 해야한다.
                    .and()
                .formLogin()
                    .loginPage("/account/login")    //andMatchers 페이지 외에 다른 페이지로 이동하면, 로그인 페이지로 자동 리다이렉트
                    .permitAll()    //로그인 페이지 접근: 모두 가능
                    .and()
                .logout()
                    .permitAll();   //로그아웃 페이지 접근: 모두 가능
    }
}
