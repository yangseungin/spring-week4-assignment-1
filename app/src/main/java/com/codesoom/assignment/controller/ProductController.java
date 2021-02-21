package com.codesoom.assignment.controller;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.dto.ProductDto;
import com.codesoom.assignment.exception.ProductNotFoundException;
import com.codesoom.assignment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//TODO        고양이 장난감 목록 얻기 - GET /products - 테스트 미작성, 장난감 등록 후 확인
//TODO        고양이 장난감 상세 조회하기 - GET /products/{id}  - 테스트 미작성, 장난감 등록 후 확인
//TODO        고양이 장난감 등록하기 - POST /products -테스트미작성, RequestBody Validation 추가해야하는가?(항상 파라미터가 다 들어온다고 가정해야 하는가?)
//TODO        고양이 장난감 수정하기 - PATCH /products/{id}   -테스트 미작성
//TODO        고양이 장난감 삭제하기 - DELETE /products/{id}  -테스트 미작성

/**
 * 상품 정보와 관련된 HTTP 요청을 처리합니다.
 */
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

    /**
     * 모든 상품의 리스트를 반환합니다.
     *
     * @return 모든 상품의 list
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    /**
     * id에 해당하는 상품을 반환합니다.
     *
     * @param id 조회할 상품의 id
     * @return 조회한 상품의 정보
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductDto getProduct(@PathVariable Long id) {
        return new ProductDto(productService.getProduct(id));
    }

    /**
     * 상품을 저장하고 저장된 상품을 반환합니다.
     *
     * @param productDto 저장할 상품의 정보
     * @return 저장된 상품의 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductDto productDto) {
        return new ProductDto(productService.addProduct(productDto));
    }

    /**
     * id에 해당하는 상품을 수정합니다.
     *
     * @param id 수정할 상품의 id
     * @param productDto 수정할 상품의 정보
     * @return 수정된 상품의 정보
     */
    @PatchMapping("/{id}")
    public ProductDto patchProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return new ProductDto(productService.updateProduct(id, productDto));
    }

    /**
     * id에 해당 하는 상품을 삭제합니다.
     *
     * @param id 삭제할 상품의 id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ProductNotFoundException.class)
    public String productNotFoundExceptionHandler(ProductNotFoundException exception) {
        return exception.getMessage();
    }

}
