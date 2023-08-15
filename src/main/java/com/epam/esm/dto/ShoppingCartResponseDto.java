package com.epam.esm.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class ShoppingCartResponseDto {
    private BigInteger userId;
    private List<GiftCertificateResponseDto> giftCertificates;
    private BigDecimal totalPrice;

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
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
}
