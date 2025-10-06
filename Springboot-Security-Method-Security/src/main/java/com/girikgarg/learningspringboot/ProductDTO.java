package com.girikgarg.learningspringboot;

public class ProductDTO {
    private Long productId;

    public ProductDTO() {

    }

    public ProductDTO(Long productId) {
        this.productId = productId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
