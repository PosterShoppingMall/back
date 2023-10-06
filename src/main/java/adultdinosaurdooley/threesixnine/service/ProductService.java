package adultdinosaurdooley.threesixnine.service;

import adultdinosaurdooley.threesixnine.dto.ProductDetailDTO;
import adultdinosaurdooley.threesixnine.dto.ProductListDTO;
import adultdinosaurdooley.threesixnine.entity.Product;
import adultdinosaurdooley.threesixnine.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    // 전체 이미지, sort 작업 필요
    public ResponseEntity<ProductDetailDTO> findProductById(Long productId) {
        Optional<Product> byId = productRepository.findById(productId);
        Product product = byId.get();
        ProductDetailDTO productDetailDTO = ProductDetailDTO
                .builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productSize(product.getProductSize())
                .productContents(product.getProductContents())
                .productPrice(product.getProductPrice())
                .build();
        return ResponseEntity.status(200).body(productDetailDTO);
    }

    // 페이징 작업 중 (sort)
    public ResponseEntity<List<ProductListDTO>> findAllProductList(int page) {
        Page<Product> productList;
        Pageable pageable = PageRequest.of(page, 20);
        productList = productRepository.findByOrderByIdDesc(pageable);
        return ResponseEntity.status(200).body(convertToDTOList(productList));
    }

    public ResponseEntity<List<ProductListDTO>> findProductByKeyword(String keyword, int page) {
        Page<Product> productList;
        Pageable pageable = PageRequest.of(page, 20);
        productList = productRepository.findByProductNameContainingOrderByIdDesc(pageable, keyword);
        return ResponseEntity.status(200).body(convertToDTOList(productList));
    }

    public ResponseEntity<List<ProductListDTO>> findBestProductList(int page) {
        Page<Product> productList;
        Pageable pageable = PageRequest.of(page, 20);
        productList = productRepository.findTop30ByOrderBySellAmountDesc(pageable);
        return ResponseEntity.status(200).body(convertToDTOList(productList));
    }

    public ResponseEntity<List<ProductListDTO>> findProductListByCategory(String category, int page) {
        Page<Product> productList;
        Pageable pageable = PageRequest.of(page, 20);
        productList = productRepository.findByCategoryOrderByIdDesc(pageable, category);
        return ResponseEntity.status(200).body(convertToDTOList(productList));
    }


    public List<ProductListDTO> convertToDTOList(Page<Product> page) {
        return page.getContent()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProductListDTO convertToDTO(Product product) {
        ProductListDTO dto = ProductListDTO
                .builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .stockAmount(product.getStock().getStockAmount())
                .build();
        return dto;
    }

}
