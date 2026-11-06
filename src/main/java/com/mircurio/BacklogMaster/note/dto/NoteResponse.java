package com.mircurio.BacklogMaster.note.dto;

import com.mircurio.BacklogMaster.category.dto.CategoryResponse;

import java.time.LocalDateTime;

public record NoteResponse(
    Long id,
    String title,
    String content,
    CategoryResponse category,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}