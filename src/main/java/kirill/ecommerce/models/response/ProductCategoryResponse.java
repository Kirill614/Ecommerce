package kirill.ecommerce.models.response;

import kirill.ecommerce.models.dto.CategoryDto;

public class ProductCategoryResponse {
    private CategoryDto category;

    public ProductCategoryResponse(CategoryDto category){
        this.category = category;
    }

    public CategoryDto getCategory(){
        return this.category;
    }
}
