package org.kimbs.querydsl.student.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.sql.SQLExpressions;
import com.querydsl.sql.SQLQueryFactory;
import org.kimbs.querydsl.student.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.kimbs.querydsl.department.domain.QDepartment.department;
import static org.kimbs.querydsl.student.domain.QStudent.student;

public class StudentCustomRepositoryImpl extends QuerydslRepositorySupport implements StudentCustomRepository {

    @Autowired
    private SQLQueryFactory sqlQueryFactory;

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

    @Transactional
    @Override
    public List<Tuple> fromClauseSubQueryExampleWithJoin() {
        StringExpression query1 = Expressions.stringPath("dname");
        StringExpression query2 = Expressions.stringPath("sname");

        return sqlQueryFactory.select(query1, query2)
                .from(
                        SQLExpressions
                        .select(department.name.as("dname"), student.name.as("sname"))
                        .from(department)
                        .innerJoin(student)
                        .on(department.id.eq(student.department.id))
                        .orderBy(student.name.desc()).as("dual")
                )
                .fetch();
    }

    @Transactional
    @Override
    public List<Tuple> fromClauseSubQueryExample() {
        StringExpression sName = Expressions.stringPath("STUDENT_NAME");
        StringExpression sAddress = Expressions.stringPath("STUDENT_ADDRESS");

        return sqlQueryFactory.select(sName, sAddress)
                .from(
                        SQLExpressions
                        .select(student.name.as("STUDENT_NAME"), student.address.as("STUDENT_ADDRESS"))
                        .from(student)
                        .orderBy(student.name.desc()).as("dual")
                )
                .fetch();
    }
}
