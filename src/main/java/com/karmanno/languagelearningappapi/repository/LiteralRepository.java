package com.karmanno.languagelearningappapi.repository;

import com.karmanno.languagelearningappapi.domain.Language;
import com.karmanno.languagelearningappapi.entity.Literal;
import com.karmanno.languagelearningappapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LiteralRepository extends JpaRepository<Literal, Integer> {
    List<Literal> findAllByUserAndSource(User user, Language source);
    List<Literal> findAllByUserAndDestination(User user, Language destination);
}
