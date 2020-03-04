package org.kimbs.querydsl.student.repository;

import com.querydsl.core.BooleanBuilder;
import static org.kimbs.querydsl.student.domain.QStudent.student;
import org.kimbs.querydsl.student.domain.Student;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

public class StudentCustomRepositoryImpl extends QuerydslRepositorySupport implements StudentCustomRepository {

    public StudentCustomRepositoryImpl() {
        super(Student.class);
    }


    @Override
    public List<Student> findStudentNameAndAddressAndYearDynamicQuery(String name, String address, Integer year) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (!StringUtils.isEmpty(name)) {
            booleanBuilder.and(student.name.eq(name));
        }
        if (!StringUtils.isEmpty(address)) {
            booleanBuilder.and(student.address.eq(address));
        }
        if (year != null) {
            booleanBuilder.and(student.year.eq(year));
        }

        return from(student)
                .where(booleanBuilder)
                .fetch();
    }

    @Override
    public List<Student> findStudentByUnassignedDepartment() {
        return from(student)
                .where(student.department.id.isNull())
                .fetch();
    }
}
