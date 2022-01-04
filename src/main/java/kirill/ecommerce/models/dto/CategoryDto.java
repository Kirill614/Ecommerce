package kirill.ecommerce.models.dto;

public class CategoryDto {
    private String name;

    public CategoryDto(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public CategoryDto(){ }
}
