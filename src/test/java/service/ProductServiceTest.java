package service;

import kirill.ecommerce.converter.ProductConverter;
import kirill.ecommerce.converter.ProductVariantConverter;
import kirill.ecommerce.models.dto.CategoryDto;
import kirill.ecommerce.models.dto.ProductDto;
import kirill.ecommerce.models.dto.ProductVariantDto;
import kirill.ecommerce.models.entity.Product;
import kirill.ecommerce.models.entity.ProductCategory;
import kirill.ecommerce.models.entity.ProductVariant;
import kirill.ecommerce.models.entity.Supplier;
import kirill.ecommerce.models.request.AddProductRequest;
import kirill.ecommerce.models.request.ProductVariantRequest;
import kirill.ecommerce.repository.ProductRepository;
import kirill.ecommerce.repository.ProductVariantsRepository;
import kirill.ecommerce.service.ProductCacheServiceImpl;
import kirill.ecommerce.service.ProductCategoryService;
import kirill.ecommerce.service.ProductServiceImpl;
import kirill.ecommerce.service.supplier.SupplierService;
import org.checkerframework.checker.units.qual.A;
import org.hibernate.procedure.ProcedureOutputs;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductCacheServiceImpl cacheService;

    @Mock
    ProductConverter productConverter;

    @Mock
    ProductVariantConverter variantConverter;

    @Mock
    ProductVariantsRepository productVariantRepository;

    @Mock
    SupplierService supplierService;

    @Mock
    ProductCategoryService categoryService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void should_find_all_products() {
        List<Product> list = new ArrayList<Product>();
        list.add(new Product("a", "b", "c", null, null));
        list.add(new Product("d", "e", "f", null, null));
        list.add(new Product("g", "h", "i", null, null));

        when(cacheService.findAllProducts()).thenReturn(list);

        List<Product> expectedList = productService.findAllProducts();
        assertEquals(list, expectedList);
        verify(cacheService, times(1)).findAllProducts();
    }

    @Test
    public void should_add_product_for_supplier() {
        //given
        String username = "";
        String categoryName = "";
        Supplier expectedSupplier = new Supplier();
        ProductCategory productCategory = new ProductCategory();
        Product product = new Product();
        AddProductRequest request = new AddProductRequest();
        request.setProductDto(new ProductDto("", "", "", new CategoryDto("")));

        when(supplierService.getSupplier()).thenReturn(new Supplier());
        when(categoryService.findByName(categoryName)).thenReturn(productCategory);
        when(productConverter.convertFromDto(request.getProductDto())).thenReturn(product);
        when(supplierService.saveSupplier(any(Supplier.class))).thenReturn(expectedSupplier);

        ArgumentCaptor<Supplier> supplierArgumentCaptor = ArgumentCaptor.forClass(Supplier.class);

        //when
        Supplier supplierResult = productService.addProductSupplier(request);

        //then
        verify(supplierService).saveSupplier(supplierArgumentCaptor.capture());
        then(supplierResult).isEqualTo(expectedSupplier);
    }

    @Test
    public void should_search_product() {
        //given
        String keyword = "";
        int page = 0;
        int size = 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<Product> expectedList = Stream.generate(() -> new Product())
                .limit(2)
                .collect(Collectors.toList());

        when(cacheService.searchProduct(keyword, pageRequest)).thenReturn(expectedList);
        //when
        List<Product> listResult = productService.searchProduct(keyword, page, size);
        //then
        verify(cacheService).searchProduct(keyword, pageRequest);
        then(listResult).isEqualTo(expectedList);
    }

    @Test
    public void should_find_product_by_name() {
        String name = "";
        Product expectedProduct = new Product();

        when(cacheService.findByName(name)).thenReturn(expectedProduct);

        Product product = productService.findByName(name);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        verify(cacheService).findByName(argumentCaptor.capture());
        then(expectedProduct).isEqualTo(product);
    }

    @Test
    public void should_save_product_variant() {
        //given
        ProductVariant productVariant = new ProductVariant();
        Product product = new Product();
        product.setProductVariantList(new ArrayList<ProductVariant>());
        ProductVariantRequest request = new ProductVariantRequest(0, new ProductVariantDto());
        when(variantConverter.convertFromDto(any(ProductVariantDto.class))).thenReturn(productVariant);
        when(productService.findById(any(int.class))).thenReturn(product);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ArgumentCaptor<Product> argumentCaptor = ArgumentCaptor
                .forClass(Product.class);
        //when
        Product resultProduct = productService.addProductVariant(request);

        //then
        verify(productRepository).save(argumentCaptor.capture());
        then(resultProduct).isEqualTo(product);
    }

}
