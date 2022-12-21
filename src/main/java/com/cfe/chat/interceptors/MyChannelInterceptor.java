package com.cfe.chat.interceptors;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

public class MyChannelInterceptor
//        implements ChannelInterceptor
{

//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
////        StompCommand command = accessor.getStompCommand();
//        // ...
//        return message;
//    }
}
