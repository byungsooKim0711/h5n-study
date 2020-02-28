package org.kimbs.querydsl.clazz.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


/*
 * id: 강좌 아이디
 * title: 강좌 제목
 * credit: 강좌 학점
 */
@Entity
@Getter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "TITLE", length = 10, nullable = false)
    private String title;

    @Column(name = "CREDIT")
    private int credit;

    @Builder
    public Course(String title, int credit) {
        this.title = title;
        this.credit = credit;
    }
}
