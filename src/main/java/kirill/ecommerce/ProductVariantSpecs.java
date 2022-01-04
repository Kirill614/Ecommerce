package kirill.ecommerce;

import kirill.ecommerce.models.entity.ProductVariant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProductVariantSpecs {
    public static Specification<ProductVariant> category(String category){
        return (root, query, cb) -> {
            return cb.equal(root.join("product").join("category").get("name"), category);
        };
    }

    public static Specification<ProductVariant> minPrice(Float minPrice){
        return (root, query, cb) -> {
            return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    public static Specification<ProductVariant> maxPrice(Float maxPrice){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.lessThan(root.get("price"), maxPrice);
        };
    }

}
