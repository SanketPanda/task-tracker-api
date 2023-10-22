package com.task.tracker.service.article.exception;

import com.task.tracker.exception.BaseServiceException;
import org.springframework.http.HttpStatus;

public class ArticleNotFoundException extends BaseServiceException {

    private static final long serialVersionUID = -3177060643448007226L;

    private final HttpStatus status = HttpStatus.FORBIDDEN;

    private static final String errorMessage = "Article not found with name: %s.";

    public ArticleNotFoundException(String exception) {
        super(String.format(errorMessage, exception));
    }

    public ArticleNotFoundException(String errorMessage, Long articleId){
        super(String.format(errorMessage,articleId));
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}