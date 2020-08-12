package com.humuson.imc.admin.web.domain.admin.repository;

import com.humuson.imc.admin.web.domain.user.repository.WebUserAuthor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserAuthorRepository extends JpaRepository<WebUserAuthor, Integer> {

}
