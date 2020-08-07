package com.humuson.imc.admin.web.common.converter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
    Entity -> Response(dto)
 */
public interface Converter<E, D> {

    @NotNull
    D toDto(E entity);

    @NotNull
    List<D> toDto(List<E> entities);
}
