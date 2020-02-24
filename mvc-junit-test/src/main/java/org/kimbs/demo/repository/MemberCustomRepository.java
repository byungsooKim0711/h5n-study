package org.kimbs.demo.repository;

import org.kimbs.demo.model.Member;

import java.util.List;

public interface MemberCustomRepository {
    List<Member> findMemberByEmail(String email);

    List<Member> findMemberByName(String name);

    List<Member> findMemberByScoreGreaterThanEqual(int score);
}
