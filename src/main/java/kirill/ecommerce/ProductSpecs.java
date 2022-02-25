package kirill.ecommerce;

import kirill.ecommerce.models.entity.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecs {
    public static Specification<Product> category(String category) {
        return((root, query, cb) -> cb.equal(root.join("category").get("name"), category));
    }
}
