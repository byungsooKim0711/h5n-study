package org.kimbs.demo.repository;

import org.kimbs.demo.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberCustomRepository {
    List<Member> findMemberByEmail(String email);

    List<Member> findMemberByName(String name);

    List<Member> findMemberByScoreGreaterThanEqual(int score);

    Optional<Member> findMemberById(long id);

    List<Member> findMemberByNameContainingOrderByNameAscAndOrderByScoreDesc(String name);

    List<Member> findAllMemberInnerJoinClub();

    List<Member> findMemberByClubNameInnerJoinClub(String clubName);
}
