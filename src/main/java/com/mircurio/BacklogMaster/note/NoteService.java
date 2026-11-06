package com.mircurio.BacklogMaster.note;

import com.mircurio.BacklogMaster.category.Category;
import com.mircurio.BacklogMaster.category.CategoryRepository;
import com.mircurio.BacklogMaster.category.dto.CategoryResponse;
import com.mircurio.BacklogMaster.note.dto.NoteRequest;
import com.mircurio.BacklogMaster.note.dto.NoteResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class NoteService {

    private final NoteRepository noteRepository;
    private final CategoryRepository categoryRepository;

    public NoteService(NoteRepository noteRepository, CategoryRepository categoryRepository) {
        this.noteRepository = noteRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<NoteResponse> getAll() {
        return noteRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public NoteResponse getById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note " + id + " not found"));
        return toResponse(note);
    }

    @Transactional
    public NoteResponse create(NoteRequest request) {
        Note note = new Note(request.title(), request.content());

        if (request.categotyId() != null) {
            Category category = categoryRepository.findById(request.categotyId())
                    .orElseThrow(() -> new RuntimeException("Category " + request.categotyId() + " not found"));
            note.setCategory(category);
        }

        Note saved = noteRepository.save(note);
        return toResponse(saved);
    }

    @Transactional
    public NoteResponse update(Long id, NoteRequest request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note " + id + " not found"));
        note.setTitle(request.title());
        note.setContent(request.content());

        if (request.categotyId() != null) {
            Category category = categoryRepository.findById(request.categotyId())
                    .orElseThrow(() -> new RuntimeException("Category " + request.categotyId() + " not found"));
            note.setCategory(category);
        }
        else
        {
            note.setCategory(null);
        }

        return toResponse(note);
    }

    @Transactional
    public void delete(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note " + id + " not found");
        }
        noteRepository.deleteById(id);
    }

    private NoteResponse toResponse(Note note) {
        CategoryResponse categoryResponse = null;
        if (note.getCategory() != null) {
            Category c = note.getCategory();
            categoryResponse = new CategoryResponse(c.getId(), c.getName(), c.getCreatedAt());
        }

        return new NoteResponse(
                note.getId(),
                note.getTitle(),
                note.getContent(),
                categoryResponse,
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }
}
