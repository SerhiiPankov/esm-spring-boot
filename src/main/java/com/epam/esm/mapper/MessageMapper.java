package com.epam.esm.mapper;

import com.epam.esm.dto.MessageResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    public MessageResponseDto mapToTagResponseDto(String message) {
        MessageResponseDto messageResponseDto = new MessageResponseDto();
        messageResponseDto.setMessage(message);
        return messageResponseDto;
    }
}
