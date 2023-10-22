package com.task.tracker.service.article.exception;

import com.task.tracker.exception.DuplicateDataException;

public class DuplicateArticleException extends DuplicateDataException {

    private static final long serialVersionUID = -7246376368488589887L;

    private static final String errorMessage = "Article already exist with name: %s for activity: %s.";

    public DuplicateArticleException(String articleName, String ticketTitle) {
        super(String.format(errorMessage, articleName, ticketTitle));
    }
}