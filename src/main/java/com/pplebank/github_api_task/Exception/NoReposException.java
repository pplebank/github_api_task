package com.pplebank.github_api_task.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoReposException extends RuntimeException {
    public NoReposException(String message)  {
        super(message);
    }
}
