package com.humuson.imc.admin.web.dto.converter;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface Converter<E, D> {

    @NotNull
    D toDto(E entity);

    @NotNull
    List<D> toDto(List<E> entities);
}
