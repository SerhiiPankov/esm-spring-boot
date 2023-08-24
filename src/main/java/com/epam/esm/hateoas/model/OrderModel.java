package com.epam.esm.hateoas.model;

import com.epam.esm.dto.GiftCertificateResponseDto;
import com.epam.esm.dto.UserResponseDto;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.hateoas.RepresentationModel;

public class OrderModel extends RepresentationModel<OrderModel> {
    private BigInteger id;
    private UserResponseDto user;
    private List<GiftCertificateResponseDto> giftCertificates;
    private BigDecimal totalPrice;
    private LocalDateTime purchaseDate;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public UserResponseDto getUser() {
        return user;
    }

    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    public List<GiftCertificateResponseDto> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(List<GiftCertificateResponseDto> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
