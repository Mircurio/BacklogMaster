package com.mircurio.BacklogMaster.category;

import com.mircurio.BacklogMaster.category.dto.CategoryRequest;
import com.mircurio.BacklogMaster.category.dto.CategoryResponse;
import com.mircurio.BacklogMaster.common.exception.CategoryAlreadyExistsException;
import com.mircurio.BacklogMaster.common.exception.CategoryInUseException;
import com.mircurio.BacklogMaster.common.exception.CategoryNotFoundException;
import com.mircurio.BacklogMaster.note.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final NoteRepository noteRepository;

    public CategoryService(CategoryRepository categoryRepository, NoteRepository noteRepository) {
        this.categoryRepository = categoryRepository;
        this.noteRepository = noteRepository;
    }

    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        return toResponse(category);
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsByName(request.name())) {
            throw new CategoryAlreadyExistsException(request.name());
        }

        Category category = new Category(request.name());

        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        category.setName(request.name());

        return toResponse(category);
    }

    @Transactional
    public void delete(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        // Проверяем, есть ли заметки в этой категории
        long notesCount = noteRepository.countByCategoryId(id);

        if (notesCount > 0) {
            throw new CategoryInUseException(id, notesCount);
        }

        categoryRepository.deleteById(id);
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName(), category.getCreatedAt());
    }
}
