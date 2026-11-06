package com.mircurio.BacklogMaster.category.dto;

import java.time.LocalDateTime;

public record CategoryResponse (
    Long id,
    String name,
    LocalDateTime createdAt
) {}
