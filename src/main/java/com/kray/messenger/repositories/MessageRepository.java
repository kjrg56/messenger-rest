package com.kray.messenger.repositories;

import com.kray.messenger.model.Message;

import javax.inject.Singleton;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Singleton
public abstract class MessageRepository {

    private static Map<Long, Message> messages = new HashMap<>();

    public static Map<Long, Message> getMessages() {
        Message m1 = new Message(1, "Hi", "Kevin", Calendar.getInstance().getTime());
        messages.put((long) 1, m1);
        return messages;
    }

}
