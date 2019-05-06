package com.juyoung.restapiwithspring.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;

@Configuration
@EnableResourceServer
// 리소스 접근이 필요할 때, token service auth server에서 토큰을 확인하는.. 인증 정보가 있는지 없는지 확인, 접근 제한을 한다.
// 같이써도 상관은 없으나... resource server는 각 application에서 다룬다.
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        // 최소한 resource id는 바꿔야한다.
//        resources.accessDeniedHandler() // 접근권한이 없는 경우
        resources.resourceId("event");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .anonymous()
                .and()
            .authorizeRequests()
                .mvcMatchers(HttpMethod.GET, "/api/**")
//                    .anonymous() //  인증 안됫을때만 사용가능
                    .permitAll()    // 모두 사용가능
                .anyRequest()
                    .authenticated()
                .and()
            .exceptionHandling()// 인증 이 잘못된경우, 권한이 잘못된 경우
                .accessDeniedHandler(new OAuth2AccessDeniedHandler()); // 접근권한이 없는 경우에 exception을 하겠다
    }
}

