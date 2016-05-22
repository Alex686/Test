package com.alex.test.saiku.olap.query2.util.olap.query2.dto.service.datasource;



public class RepositoryException extends Exception {
    public RepositoryException() {
    }

    public RepositoryException(String message) {
        super(message);
    }

    public RepositoryException(String message, Throwable rootCause) {
        super(message, rootCause);
    }

    public RepositoryException(Throwable rootCause) {
        super(rootCause);
    }
}
