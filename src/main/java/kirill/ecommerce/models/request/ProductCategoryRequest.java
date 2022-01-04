package kirill.ecommerce.models.request;

import kirill.ecommerce.models.dto.CategoryDto;

public class ProductCategoryRequest {
    private CategoryDto categoryDto;

    public ProductCategoryRequest(CategoryDto categoryDto){
        this.categoryDto = categoryDto;
    }

    public ProductCategoryRequest(){ }

    public CategoryDto getCategoryDto() {
        return categoryDto;
    }

    public void setCategoryDto(CategoryDto categoryDto) {
        this.categoryDto = categoryDto;
    }
}
