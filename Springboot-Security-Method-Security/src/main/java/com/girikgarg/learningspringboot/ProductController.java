package com.girikgarg.learningspringboot;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
    @GetMapping("/products")
    @PostAuthorize("returnObject.productId == authentication.principal.id")
    public ProductDTO getProducts() {
        ProductDTO product = new ProductDTO();
        product.setProductId(1L);
        System.out.println("Product controller running");
        return product;
    }

}
