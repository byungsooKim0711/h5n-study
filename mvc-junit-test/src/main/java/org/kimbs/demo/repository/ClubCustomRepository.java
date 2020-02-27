package org.kimbs.demo.repository;

import org.kimbs.demo.model.Club;

import java.util.List;
import java.util.Optional;

public interface ClubCustomRepository {
    public Optional<Club> findAllByMemberId(long memberId);

    public List<Club> dynamicQuery(String name, String office, String telephoneNumber);
}
