package org.kimbs.querydsl.student.repository;

import com.querydsl.core.Tuple;
import org.kimbs.querydsl.student.domain.Student;

import java.util.List;

public interface StudentCustomRepository {

    List<Student> findStudentNameAndAddressAndYearDynamicQuery(String name, String address, Integer year);

    List<Student> findStudentByUnassignedDepartment();

    List<Tuple> fromClauseSubQueryExampleWithJoin();

    List<Tuple> fromClauseSubQueryExample();
}
