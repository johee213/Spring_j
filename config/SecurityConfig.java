package com.example.masil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity(prePostEnabled = true)
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/chat/**")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                );

        http
                .authorizeHttpRequests((auth) -> auth
                        // 1. 누구나 접근 가능한 정적 리소스 및 기본 페이지
                        .requestMatchers("/", "/css/**", "/js/**", "/img/**", "/images/**", "/favicon.ico").permitAll()
                        .requestMatchers("/error", "/error/**").permitAll()
                        .requestMatchers("/api/chat/**").permitAll()
                        .requestMatchers("/question/list", "/faq/list").permitAll()

                        // 2. 회원가입 관련
                        .requestMatchers("/siteUser/login", "/siteUser/chuga", "/siteUser/chugaProc", "/siteUser/check-username").permitAll()

                        // 3. 영상 파일
                        .requestMatchers("/video_files/**").authenticated()

                        // 🌟 [추가] 퀴즈 관련 권한 세부 분리 보호 설정
                        // 퀴즈 등록, 수정, 삭제 처리는 무조건 ADMIN 또는 INSTRUCTOR 권한을 가진 사람만 가능하게 제한!
                        .requestMatchers("/quiz/chuga/**", "/quiz/sujung/**", "/quiz/sakjeProc").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers("/quiz/list").hasAnyRole("ADMIN", "INSTRUCTOR")

                        // 수강생(일반 유저)이 시험을 치고 조회해야 하는 view 화면은 로그인한 회원이면 누구나 통과!
                        .requestMatchers("/quiz/view/**").authenticated()

                        // 강사 관리 영역 보호 설정
                        .requestMatchers("/teacher/main", "/teacher/give-stamp").hasAnyRole("ADMIN", "INSTRUCTOR")
                        .requestMatchers("/teacher/list", "/teacher/view/**").hasRole("ADMIN")

                        // 4. [마지막] 그 외 모든 요청은 인증(로그인) 필요
                        .requestMatchers("/", "/index", "/main").permitAll() // 추가
                        .anyRequest().authenticated()

                );
        http
                .formLogin((auth) -> auth
                        .loginPage("/siteUser/login")
                        .defaultSuccessUrl("/")
                        .failureUrl("/siteUser/login?error=true")
                );
        http
                .rememberMe(remember -> remember
                        .key("masil-secret-key")
                        .tokenValiditySeconds(86400) // 1일
                );

        http
                .logout((auth) -> auth
                        .logoutRequestMatcher(new AntPathRequestMatcher("/siteUser/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}