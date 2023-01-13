package com.cfe.chat.config;

import ch.qos.logback.classic.pattern.MessageConverter;
import com.cfe.chat.interceptors.MyChannelInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private TaskScheduler messageBrokerTaskScheduler;

    @Autowired
    public void setMessageBrokerTaskScheduler(@Lazy TaskScheduler taskScheduler) {
        this.messageBrokerTaskScheduler = taskScheduler;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
//        messageBrokerRegistry.enableStompBrokerRelay("/public", "/user")
////                .setHeartbeatValue(new long[] {10000, 20000})
////                .setTaskScheduler(this.messageBrokerTaskScheduler)
//                .setRelayHost("localhost")
////                .setRelayPort(15672) // for stomp web-client
//                .setRelayPort(61613) // for stomp client
//                .setSystemLogin("guest")
//                .setSystemPasscode("guest");
//        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
//        messageBrokerRegistry.setUserDestinationPrefix("/user");
//        messageBrokerRegistry.setPreservePublishOrder(true);

        messageBrokerRegistry.enableSimpleBroker("/public", "/user")
                .setHeartbeatValue(new long[] {10000, 20000})
                .setTaskScheduler(this.messageBrokerTaskScheduler);
//                .setRelayHost("localhost")
////                .setRelayPort(15672) // for stomp web-client
//                .setRelayPort(61613) // for stomp client
//                .setSystemLogin("guest")
//                .setSystemPasscode("guest");
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
        messageBrokerRegistry.setUserDestinationPrefix("/user");
        messageBrokerRegistry.setPreservePublishOrder(true);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry
                .addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new MyChannelInterceptor());
//    }

//    @Override
//    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
//        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setObjectMapper(new ObjectMapper());
//        converter.setContentTypeResolver(resolver);
//        messageConverters.add(converter);
//        return false;
//    }
}