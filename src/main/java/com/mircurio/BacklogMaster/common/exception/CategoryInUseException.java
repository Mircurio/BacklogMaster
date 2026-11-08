package com.mircurio.BacklogMaster.common.exception;

public class CategoryInUseException extends RuntimeException {
    public CategoryInUseException(Long id, long count) {
        super("Category " + id + " is used by " + count + " notes and cannot be deleted");
    }
}
