package com.cfe.chat.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
////import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//import static springfox.documentation.builders.PathSelectors.regex;

//@EnableWebMvc
//@Configuration
//@EnableSwagger2
public class SwaggerConfig {
//    @Bean
//    public Docket productApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.cfe.chat.controller"))
//                .paths(regex("/api.*"))
//                .build()
//                .apiInfo(metaData());
//
//    }

//    private ApiInfo metaData() {
////        return new ApiInfo(
////                "Spring Boot REST API",
////                "Spring Boot REST API for Online Store",
////                "1.0",
////                "Terms of service",
////                new Contact("Atif Amin", "https://test.com/about/", "atifami@cfe.com"),
////                "Apache License Version 2.0",
////                "https://www.apache.org/licenses/LICENSE-2.0",
////                );
//        return new ApiInfoBuilder()
//                .title("CFE Message Broker")
//                .description("CFE Message Broker and other API reference for developers")
//                .version("1.0")
//                .termsOfServiceUrl("http://termsOfServiceUrl.com")
//                .contact(new Contact("Atif Amin", "https://test.com/about/", "atifami@cfe.com"))
//                .license("Apache License Version 2.0")
//                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
//                .build();
//    }

//    @Bean
//    public static BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
//        return new BeanPostProcessor() {
//            @Override
//            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//                if (bean instanceof WebMvcRequestHandlerProvider) {
//                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
//                }
//                return bean;
//            }
//
//            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
//                List<T> copy = mappings.stream()
//                        .filter(mapping -> mapping.getPatternParser() == null)
//                        .collect(Collectors.toList());
//                mappings.clear();
//                mappings.addAll(copy);
//            }
//
//            @SuppressWarnings("unchecked")
//            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
//                try {
//                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
//                    field.setAccessible(true);
//                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
//                } catch (IllegalArgumentException | IllegalAccessException e) {
//                    throw new IllegalStateException(e);
//                }
//            }
//        };
//    }
}