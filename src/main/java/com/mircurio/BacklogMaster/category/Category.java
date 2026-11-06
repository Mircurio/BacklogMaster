package com.mircurio.BacklogMaster.category;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    // Геттеры
    public Long getId() { return  id;}
    public String getName() { return name; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Сеттеры
    public void setName(String name) { this.name = name; }
}
