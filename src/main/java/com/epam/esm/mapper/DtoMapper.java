package com.epam.esm.mapper;

public interface DtoMapper<T, Q, S> {
    T mapToModel(Q dto) throws ReflectiveOperationException;

    S mapToDto(T t);
}
