package com.cfe.chat.utils;

import com.cfe.chat.dto.ActiveInfo;
import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class ChatServerConstants {

    public static Map<Long, ActiveInfo> activeInfoMap = new ConcurrentHashMap<>();
}
