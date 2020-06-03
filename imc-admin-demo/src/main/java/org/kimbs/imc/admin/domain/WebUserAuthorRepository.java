package org.kimbs.imc.admin.domain;

import org.kimbs.imc.admin.domain.code.AuthLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserAuthorRepository extends JpaRepository<WebUserAuthor, Long> {

    WebUserAuthor findByAuthLevel(AuthLevel authLevel);
}
