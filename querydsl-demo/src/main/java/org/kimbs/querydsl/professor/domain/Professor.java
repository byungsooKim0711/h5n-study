package org.kimbs.querydsl.professor.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kimbs.querydsl.config.BaseTimeEntity;
import org.kimbs.querydsl.department.domain.Department;

import javax.persistence.*;


/*
 * id: 교수 아이디
 * name: 교수 이름
 * position: 교수 직위
 * yearEmp: 교수 고용 년도
 * departmentId: 교수가 속한 부서 아이디
 */
@Entity(name = "PROFESSOR")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", length = 10, nullable = false)
    private String name;

    @Column(name = "POSITION", length = 10)
    private String position;

    @Column(name = "YEAR_EMP")
    private int yearEmp;

    @Builder
    public Professor(String name, String position, int yearEmp) {
        this.name = name;
        this.position = position;
        this.yearEmp = yearEmp;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID", foreignKey = @ForeignKey(name = "FK_PROFESSOR_DEPARTMENT"))
    private Department department;

    public void setDepartment(Department department) {
        this.department = department;
    }
}
