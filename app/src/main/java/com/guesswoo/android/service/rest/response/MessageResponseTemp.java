package com.guesswoo.android.service.rest.response;

import com.guesswoo.api.dto.responses.MessageResponse;

public class MessageResponseTemp extends MessageResponse {

    private String messageId;

    public String getMessageId() {
        return getId();
    }

    public void setMessageId(String messageId) {
        setId(messageId);
    }
}
