package adultdinosaurdooley.threesixnine.product.service;

import adultdinosaurdooley.threesixnine.product.dto.MainPageDTO;
import adultdinosaurdooley.threesixnine.product.dto.ProductDetailDTO;
import adultdinosaurdooley.threesixnine.product.dto.ProductImageDto;
import adultdinosaurdooley.threesixnine.product.dto.ProductListDTO;
import adultdinosaurdooley.threesixnine.product.entity.Product;
import adultdinosaurdooley.threesixnine.product.entity.ProductImage;
import adultdinosaurdooley.threesixnine.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ResponseEntity<Map<String, List<MainPageDTO>>> findProductsforMainPage() {
        Map<String, List<MainPageDTO>> mainPageMap = new HashMap<>();
        List<Product> mainAllProductList
                = productRepository.findTop6ByOrderByCreatedAt(PageRequest.of(0, 6));
        mainPageMap.put("all", convertListToMainPageDTOList(mainAllProductList));
        List<Product> bestProductList
                = productRepository.findTop6ByOrderBySellAmountDesc(PageRequest.of(0, 6));
        mainPageMap.put("best", convertListToMainPageDTOList(bestProductList));
        List<Product> illustrationProductList
                = productRepository.findTop6ByCategoryOrderByCreatedAt("illustration",PageRequest.of(0, 6));
        mainPageMap.put("illustration", convertListToMainPageDTOList(illustrationProductList));
        List<Product> paintingProductList
                = productRepository.findTop6ByCategoryOrderByCreatedAt("painting",PageRequest.of(0, 6));
        mainPageMap.put("painting", convertListToMainPageDTOList(paintingProductList));
        List<Product> photographyProductList
                = productRepository.findTop6ByCategoryOrderByCreatedAt("photography",PageRequest.of(0, 6));
        mainPageMap.put("photography", convertListToMainPageDTOList(photographyProductList));
        List<Product> typographyProductList
                = productRepository.findTop6ByCategoryOrderByCreatedAt("typography",PageRequest.of(0, 6));
        mainPageMap.put("typography", convertListToMainPageDTOList(typographyProductList));
        return ResponseEntity.status(200).body(mainPageMap);
    }

    public ResponseEntity<ProductDetailDTO> findProductById(Long productId) {
        Optional<Product> byId = productRepository.findById(productId);
        Product product = byId.get();
        List<ProductImageDto> productImageDtoList = new ArrayList<>();
        for (ProductImage productImage : product.getProductImages()){
            ProductImageDto imageDto = ProductImageDto
                    .builder()
                    .imageNum(productImage.getImageNum())
                    .imagePath(productImage.getImagePath())
                    .build();
            productImageDtoList.add(imageDto);
        }

        ProductDetailDTO productDetailDTO = ProductDetailDTO
                .builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .productSize(product.getProductSize())
                .productContents(product.getProductContents())
                .productPrice(product.getProductPrice())
                .productImages(productImageDtoList)
                .build();
        return ResponseEntity.status(200).body(productDetailDTO);
    }

    public ResponseEntity<Page<ProductListDTO>> findProductByKeyword(String keyword, int size, int page, String sort) {
        Pageable pageable = setPageable(size, page, sort);
        Page<Product> productList = productRepository.findByProductNameContaining(pageable, keyword);
        return ResponseEntity.status(200).body(convertPageToDTOList(productList));
    }

    public ResponseEntity<Page<ProductListDTO>> findBestProductList(int size, int page) {
        Page<Product> productList;
        Pageable pageable = PageRequest.of(page, size);
        productList = productRepository.findTop30ByOrderBySellAmountDesc(pageable);
        return ResponseEntity.status(200).body(convertPageToDTOList(productList));
    }

    public ResponseEntity<Page<ProductListDTO>> findProductListByCategory(String category, int size, int page, String sort) {
        Pageable pageable = setPageable(size, page, sort);
        Page<Product> productList;
        if(category.equals("*")){
            productList = productRepository.findProducts(pageable);
        } else {
            productList = productRepository.findByCategory(category,pageable);
        }
        return ResponseEntity.status(200).body(convertPageToDTOList(productList));
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

//    public List<ProductListDTO> convertPageToDTOList(Page<Product> page) {
//        return page.getContent()
//                .stream()
//                .map(this::convertToProductListDTO)
//                .collect(Collectors.toList());
//    }
public Page<ProductListDTO> convertPageToDTOList(Page<Product> page) {
    List<ProductListDTO> productListDTOList = page.getContent()
            .stream()
            .map(this::convertToProductListDTO)
            .collect(Collectors.toList());

    return new PageImpl<>(productListDTOList, page.getPageable(), page.getTotalElements());
}

    public ProductListDTO convertToProductListDTO(Product product) {
        String url = String.valueOf(productRepository.findById(product.getId()).get().getProductImages().get(0).getImagePath());
        ProductListDTO dto = ProductListDTO
                .builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .imageUrl(url)
                .stockAmount(product.getStock().getStockAmount())
                .build();
        return dto;
    }

    public List<MainPageDTO> convertListToMainPageDTOList(List<Product> list) {
        return list
                .stream()
                .map(this::convertToMainPageDTO)
                .collect(Collectors.toList());
    }

    public MainPageDTO convertToMainPageDTO(Product product){
        String url = String.valueOf(productRepository.findById(product.getId()).get().getProductImages().get(0).getImagePath());
        MainPageDTO dto = MainPageDTO
                .builder()
                .productId(product.getId())
                .imageUrl(url)
                .build();
        return dto;
    }


}
