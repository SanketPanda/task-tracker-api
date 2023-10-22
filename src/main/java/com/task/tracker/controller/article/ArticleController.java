package com.task.tracker.controller.article;

import com.task.tracker.dto.article.ArticleDTO;
import com.task.tracker.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1/article")
public class ArticleController {
    
    @Autowired
    ArticleService articleService;
    
    @GetMapping("article-id/{articleId}")
    private ArticleDTO getArticleById(@PathVariable("articleId") final Long articleId){
        return articleService.getArticleById(articleId);
    }

    @GetMapping("activity-id/{activityId}")
    private List<ArticleDTO> getArticleByActivityId(@PathVariable("activityId") final Long activityId){
        return articleService.getArticleByActivityId(activityId);
    }
    
    @PostMapping("")
    private ResponseEntity<ArticleDTO> addArticle(@RequestBody @Valid final ArticleDTO articleDTO){
        ArticleDTO newArticleDTO = articleService.addArticle(articleDTO);
        return new ResponseEntity<>(newArticleDTO, HttpStatus.CREATED);
    }

    @PutMapping("")
    private ResponseEntity<Void> updateArticle(@RequestBody @Valid final ArticleDTO articleDTO){
        articleService.updateArticle(articleDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("")
    private ResponseEntity<Void> deleteArticle(@RequestBody @Valid final ArticleDTO articleDTO){
        articleService.deleteArticle(articleDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
