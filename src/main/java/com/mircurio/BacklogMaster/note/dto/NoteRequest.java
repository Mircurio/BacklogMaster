package com.mircurio.BacklogMaster.note.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NoteRequest(
        @NotBlank(message = "Title must be not blank")
        @Size(max = 200, message = "Title must be at most 200 characters")
        String title,

        String content,

        Long categotyId
) {}
