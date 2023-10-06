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
//            @RequestParam(name = "sort", required = false, defaultValue = "id-desc") String sort,
            @RequestParam(defaultValue = "1") int page) {
        if (category.equals("all")) {
            return productService.findAllProductList(page - 1);
        } else if (category.equals("best")) {
            return productService.findBestProductList(page - 1);
        } else {
            return productService.findProductListByCategory(category, page - 1);
        }
    }

    @GetMapping("/product/search")
    public ResponseEntity<List<ProductListDTO>> searchProductByNameList(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "1") int page) {
        return productService.findProductByKeyword(keyword, page - 1);
    }

}
