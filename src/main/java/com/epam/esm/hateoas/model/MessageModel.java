package com.epam.esm.hateoas.model;

import org.springframework.hateoas.RepresentationModel;

public class MessageModel extends RepresentationModel<MessageModel> {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
