package com.noteninja.noteninjabackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@org.springframework.context.annotation.Configuration
public class CorsConfiguration {

//    @Bean
//    public CorsFilter corsFilter(){
//        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource=new UrlBasedCorsConfigurationSource();
//        org.springframework.web.cors.CorsConfiguration corsConfiguration=new org.springframework.web.cors.CorsConfiguration();
//        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:4200/","*","http://localhost:4200"));
//        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin","Content-Type","Accept","Jwt-Token","Authorization","Origin, Accept","X-Requested-With" ,
//                "Access-Control-Request-Method","Access-Control-Request-Headers"));
//        corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Content-Type","Accept","Jwt-Token","Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials","Filename"));
//        corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
//        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
//        return new CorsFilter(urlBasedCorsConfigurationSource);
//
//    }
}
