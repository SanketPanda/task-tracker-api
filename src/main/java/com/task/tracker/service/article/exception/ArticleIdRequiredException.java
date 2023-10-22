package com.task.tracker.service.article.exception;

import com.task.tracker.exception.ResourceNotFoundException;

public class ArticleIdRequiredException extends ResourceNotFoundException {

    private static final long serialVersionUID = 6833807349229219486L;

    private static final String errorMessage = "Article id is required while update/delete.";

    public ArticleIdRequiredException() {
        super(errorMessage);
    }
}