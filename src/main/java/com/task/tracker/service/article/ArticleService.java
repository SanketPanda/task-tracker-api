package com.task.tracker.service.article;

import com.task.tracker.dao.ActivityRepository;
import com.task.tracker.dao.ArticleRepository;
import com.task.tracker.dto.article.ArticleDTO;
import com.task.tracker.model.Activity;
import com.task.tracker.model.Article;
import com.task.tracker.service.activity.exception.ActivityNotFoundException;
import com.task.tracker.service.article.exception.ArticleNotFoundException;
import com.task.tracker.service.article.exception.DuplicateArticleException;
import com.task.tracker.service.project.exception.ProjectIdRequiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ActivityRepository activityRepository;

    public ArticleDTO getArticleById(final Long articleId){
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: %s.", articleId));
        return ArticleMapper.INSTANCE.toDTO(article);
    }

    public List<ArticleDTO> getArticleByActivityId(final Long activityId){
        return articleRepository.findByActivity(activityId).stream().map(ArticleMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArticleDTO addArticle(final ArticleDTO articleDTO){
        final Activity activity = activityRepository.findById(articleDTO.getActivityId())
                .orElseThrow(() -> new ActivityNotFoundException("Activity not found with id: ", articleDTO.getActivityId()));
        if(isArticleExist(articleDTO)){
            throw new DuplicateArticleException(articleDTO.getArticleName(), activity.getDescription());
        }
        final Article article = ArticleMapper.INSTANCE.toEntity(articleDTO);
        article.setActivity(activity);
        final Article newArticle = articleRepository.save(article);
        return ArticleMapper.INSTANCE.toDTO(newArticle);
    }

    @Transactional
    public void updateArticle(final ArticleDTO articleDTO){
        Optional.ofNullable(articleDTO.getArticleId()).orElseThrow(ProjectIdRequiredException::new);
        Article article = articleRepository.findById(articleDTO.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: %s.", articleDTO.getArticleId()));
        ArticleMapper.toEntity(articleDTO, article);
    }

    @Transactional
    public void deleteArticle(final ArticleDTO articleDTO){
        Optional.ofNullable(articleDTO.getArticleId()).orElseThrow(ProjectIdRequiredException::new);
        Article article = articleRepository.findById(articleDTO.getArticleId())
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: %s.", articleDTO.getArticleId()));
        articleRepository.delete(article);
    }

    private boolean isArticleExist(final ArticleDTO articleDTO){
        return articleRepository.findByNameAndActivity(articleDTO.getArticleName(), articleDTO.getActivityId()).isPresent();
    }
}
