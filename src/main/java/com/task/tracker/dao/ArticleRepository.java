package com.task.tracker.dao;

import com.task.tracker.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("SELECT a FROM Article a WHERE a.articleName = ?1 AND a.activity.activityId = ?2 ORDER BY a.createdDate DESC")
    Optional<Article> findByNameAndActivity(final String name, final Long ticketId);

    @Query("SELECT a FROM Article a WHERE a.activity.activityId = ?1 ORDER BY a.createdDate DESC")
    List<Article> findByActivity(final Long ticketId);
}
