package org.kimbs.querydsl.takes.domain;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
public class TakesId implements Serializable {

    private Long studentId;

    private Long classId;

    public TakesId(Long studentId, Long classId) {
        if (studentId != null && classId != null) {
            this.studentId = studentId;
            this.classId = classId;
        } else {
            throw new RuntimeException("아이디 확인 바람");
        }
    }
}
