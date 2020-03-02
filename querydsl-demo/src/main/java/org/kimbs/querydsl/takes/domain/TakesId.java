package org.kimbs.querydsl.takes.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TakesId implements Serializable {

    @Column(name = "STUDENT_ID")
    private Long studentId;

    @Column(name = "CLAZZ_ID")
    private Long clazzId;

    public TakesId(Long studentId, Long clazzId) {
        if (studentId != null && clazzId != null) {
            this.studentId = studentId;
            this.clazzId = clazzId;
        } else {
            throw new RuntimeException("아이디 확인 바람");
        }
    }
}
