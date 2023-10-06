package adultdinosaurdooley.threesixnine.controller;

import adultdinosaurdooley.threesixnine.dto.ProductDetailDTO;
import adultdinosaurdooley.threesixnine.dto.ProductListDTO;
import adultdinosaurdooley.threesixnine.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/369")
@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductDetailDTO> viewProductDetail(@PathVariable Long productId) {
        return productService.findProductById(productId);
    }

    @GetMapping("/product")
    public ResponseEntity<List<ProductListDTO>> viewProductList(
            @RequestParam(name = "category", required = false, defaultValue = "all") String category,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "") String sort,
            @RequestParam(defaultValue = "1") int page) {
        if (category.equals("all")) {
            return productService.findAllProductList(size, page - 1, sort);
        } else if (category.equals("best")) {
            return productService.findBestProductList(size, page - 1);
        } else {
            return productService.findProductListByCategory(category, size, page - 1, sort);
        }
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductListDTO>> searchProductByNameList(
            @RequestParam String keyword,
            @RequestParam(name = "sort", required = false, defaultValue = "") String sort,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size,
            @RequestParam(defaultValue = "1") int page) {

        return productService.findProductByKeyword(keyword, size, page - 1, sort);
    }

}
