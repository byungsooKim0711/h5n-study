package org.kimbs.querydsl.takes.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/*
 * id: 학생 아이디, 클래스 아이디 복합키
 * grade: 학점
 */
@Entity
@Getter
@NoArgsConstructor
public class Takes {

    @EmbeddedId
    private TakesId id;

    @Column(name = "GRADE", length = 2)
    private String grade;

    @Builder
    public Takes(String grade) {
        this.grade = grade;
    }
}
