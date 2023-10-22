package com.task.tracker.service.article;

import com.task.tracker.dto.activity.ActivityDTO;
import com.task.tracker.dto.article.ArticleDTO;
import com.task.tracker.model.Activity;
import com.task.tracker.model.Article;
import com.task.tracker.service.activity.ActivityMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mapping(source = "activity.activityId", target = "activityId")
    @Mapping(source = "activity", target = "activityDTO", qualifiedByName = "setActivityDTO")
    ArticleDTO toDTO(Article article);

    @Mapping(target = "articleId", ignore = true)
    Article toEntity(ArticleDTO articleDTO);

    static void toEntity(final ArticleDTO articleDTO, final Article article){
        article.setArticleName(articleDTO.getArticleName());
        article.setArticleNumber(articleDTO.getArticleNumber());
        article.setArticleCount(articleDTO.getArticleCount());
    }

    @Named("setActivityDTO")
    default ActivityDTO setActivityDTO(Activity activity){
        return ActivityMapper.INSTANCE.toDTO(activity);
    }
}
