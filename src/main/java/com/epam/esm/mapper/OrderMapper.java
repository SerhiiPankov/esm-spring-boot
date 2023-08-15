package com.epam.esm.mapper;

import com.epam.esm.dto.OrderResponseDto;
import com.epam.esm.lib.data.Page;
import com.epam.esm.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    OrderResponseDto mapToDto(Order order);

    Page<OrderResponseDto> mapPageDto(Page<Order> page);
}
