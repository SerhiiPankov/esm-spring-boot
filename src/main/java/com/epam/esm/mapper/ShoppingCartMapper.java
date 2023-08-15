package com.epam.esm.mapper;

import com.epam.esm.dto.GiftCertificateResponseDto;
import com.epam.esm.dto.ShoppingCartResponseDto;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.ShoppingCart;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {GiftCertificateMapper.class})
public abstract class ShoppingCartMapper {
    @Autowired
    protected GiftCertificateMapper giftCertificateMapper;

    @Mapping(source = "shoppingCart.user.id", target = "userId")
    @Mapping(source = "giftCertificates", target = "giftCertificates",
            qualifiedByName = "mapGiftCertificates")
    public abstract ShoppingCartResponseDto mapToDto(ShoppingCart shoppingCart);

    @Named("mapGiftCertificates")
    protected List<GiftCertificateResponseDto> mapGiftCertificates(
            List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream()
                .map(giftCertificateMapper::mapToGiftCertificateResponseDto)
                .collect(Collectors.toList());
    }
}
