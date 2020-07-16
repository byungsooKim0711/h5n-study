package com.humuson.imc.admin.config;

import com.humuson.imc.admin.domain.convertor.LocalDateTimeConverter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Convert;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseTimeEntity {

    @CreatedDate
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime modifiedAt;
}
