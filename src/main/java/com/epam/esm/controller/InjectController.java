package com.epam.esm.controller;

import com.epam.esm.dto.MessageResponseDto;
import com.epam.esm.initializer.DataInitializer;
import com.epam.esm.mapper.MessageMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inject")
public class InjectController {
    private final DataInitializer dataInitializer;
    private final MessageMapper messageMapper;

    public InjectController(DataInitializer dataInitializer,
                            MessageMapper messageMapper) {
        this.dataInitializer = dataInitializer;
        this.messageMapper = messageMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public MessageResponseDto inject() {
        dataInitializer.injectUsers();
        dataInitializer.injectGiftCertificates();
        dataInitializer.injectRelationshipsShoppingCartWithGiftCertificates();
        dataInitializer.injectCompletedOrders();
        return messageMapper.mapToTagResponseDto(" Users were injected, "
                + " certificate was injected.");
    }
}
