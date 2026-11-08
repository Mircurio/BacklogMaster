package com.mircurio.BacklogMaster.common.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Long id) {
        super("Note not found: " + id);
    }
}
