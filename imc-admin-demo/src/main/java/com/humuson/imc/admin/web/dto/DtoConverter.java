package com.humuson.imc.admin.web.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface DtoConverter<E, D> {

    @NotNull
    D convertDto(E entity);

    @NotNull
    List<D> convertDto(List<E> entities);

    @NotNull
    E convertEntity(D dto);

    @NotNull
    List<E> convertEntity(List<D> dtos);

}
