package fr.vitalitte.vitalittebackend.stationery.category.rest;

import fr.vitalitte.vitalittebackend.authentification.jwt.JwtUtils;
import fr.vitalitte.vitalittebackend.authentification.usecase.JwtService;
import fr.vitalitte.vitalittebackend.stationery.category.usecase.CategoryService;
import fr.vitalitte.vitalittebackend.common.models.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    CategoryService categoryService;
    JwtService jwtService;

    public CategoryController(CategoryService categoryService, JwtService jwtService) {
        this.categoryService = categoryService;
        this.jwtService = jwtService;
    }

    @PostMapping("")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> createCategory(@RequestBody CreateCategoryBody createCategoryBody) {
        categoryService.createCategory(createCategoryBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new MessageResponse("Categorie créée avec succès."));
    }

    @GetMapping("")
    public List<CategoryDto> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @PutMapping("/{slug}")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> updateCategoryBySlug(@PathVariable String slug, @RequestBody CategoryDto categoryDto) {
        categoryService.updateCategory(slug, categoryDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Catégorie mise à jour avec succès."));
    }

    @DeleteMapping("/{slug}")
    @PreAuthorize("@jwtService.isRoleAdminAndTokenNotExpired()")
    public ResponseEntity<MessageResponse> deleteCategory(@PathVariable String slug) {
        categoryService.deleteCategoryBySlug(slug);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new MessageResponse("Catégorie supprimée avec succès."));
    }
}
