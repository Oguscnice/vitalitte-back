package fr.vitalitte.vitalittebackend.category.rest;

import fr.vitalitte.vitalittebackend.category.usecase.CategoryService;
import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    CategoryService categoryService;
    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createCategory(@RequestBody String categoryName) {
        this.categoryService.createCategory(categoryName);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Categorie créée avec succès."));
    }

    @GetMapping("")
    public List<CategoryDto> getAllCategories() {
        return this.categoryService.findAllCategories();
    }

    @PutMapping("/{slug}")
    public ResponseEntity<MessageResponse> updateCategoryBySlug(@PathVariable String slug, @RequestBody CategoryDto categoryDto) {
        this.categoryService.updateCategory(slug, categoryDto);
        return ResponseEntity.ok(new MessageResponse("Catégorie mise à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable String slug) {
        this.categoryService.deleteCategoryBySlug(slug);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Categorie supprimée avec succès."));
    }
}
