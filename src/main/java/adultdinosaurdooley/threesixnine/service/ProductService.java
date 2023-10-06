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

    // 전체 이미지
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

    public ResponseEntity<List<ProductListDTO>> findAllProductList(int size, int page, String sort) {
        Pageable pageable = setPageable(size, page, sort);
        Page<Product> productList;
        productList = productRepository.findProducts(pageable);
        return ResponseEntity.status(200).body(convertToDTOList(productList));
    }

    public ResponseEntity<List<ProductListDTO>> findProductByKeyword(String keyword, int size, int page, String sort) {
        Pageable pageable = setPageable(size, page, sort);
        Page<Product> productList = productRepository.findByProductNameContaining(pageable, keyword);
        return ResponseEntity.status(200).body(convertToDTOList(productList));
    }

    public ResponseEntity<List<ProductListDTO>> findBestProductList(int size, int page) {
        Page<Product> productList;
        Pageable pageable = PageRequest.of(page, size);
        productList = productRepository.findTop30ByOrderBySellAmountDesc(pageable);
        return ResponseEntity.status(200).body(convertToDTOList(productList));
    }

    public ResponseEntity<List<ProductListDTO>> findProductListByCategory(String category, int size, int page, String sort) {
        Pageable pageable = setPageable(size, page, sort);
        Page<Product> productList;
        productList = productRepository.findByCategory(pageable, category);
        return ResponseEntity.status(200).body(convertToDTOList(productList));
    }

    private Pageable setPageable(int size, int page, String sort) {
        Pageable pageable;
        Sort commonSort = Sort.by("id").descending();
        if (sort.equals("priceAsc")) {
            pageable = PageRequest.of(page, size, Sort.by("productPrice").ascending().and(commonSort));
        } else if (sort.equals("priceDesc")) {
            pageable = PageRequest.of(page, size, Sort.by("productPrice").descending().and(commonSort));
        } else if (sort.equals("best")) {
            pageable = PageRequest.of(page, size, Sort.by("s.sellAmount").descending().and(commonSort));
        } else {
            pageable = PageRequest.of(page, size, commonSort);
        }
        return pageable;
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
