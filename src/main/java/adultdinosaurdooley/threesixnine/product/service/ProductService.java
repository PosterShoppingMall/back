package adultdinosaurdooley.threesixnine.product.service;

import adultdinosaurdooley.threesixnine.product.dto.MainPageDTO;
import adultdinosaurdooley.threesixnine.product.dto.ProductDetailDTO;
import adultdinosaurdooley.threesixnine.product.dto.ProductImageDto;
import adultdinosaurdooley.threesixnine.product.dto.ProductListDTO;
import adultdinosaurdooley.threesixnine.product.entity.ProductEntity;
import adultdinosaurdooley.threesixnine.product.entity.ProductImageEntity;
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
        List<ProductEntity> mainAllProductListEntity
                = productRepository.findTop6ByOrderByCreatedAt(PageRequest.of(0, 6));
        mainPageMap.put("all", convertListToMainPageDTOList(mainAllProductListEntity));
        List<ProductEntity> bestProductListEntity
                = productRepository.findTop6ByOrderBySellAmountDesc(PageRequest.of(0, 6));
        mainPageMap.put("best", convertListToMainPageDTOList(bestProductListEntity));
        List<ProductEntity> illustrationProductListEntity
                = productRepository.findTop6ByCategoryOrderByCreatedAt("illustration",PageRequest.of(0, 6));
        mainPageMap.put("illustration", convertListToMainPageDTOList(illustrationProductListEntity));
        List<ProductEntity> paintingProductListEntity
                = productRepository.findTop6ByCategoryOrderByCreatedAt("painting",PageRequest.of(0, 6));
        mainPageMap.put("painting", convertListToMainPageDTOList(paintingProductListEntity));
        List<ProductEntity> photographyProductListEntity
                = productRepository.findTop6ByCategoryOrderByCreatedAt("photography",PageRequest.of(0, 6));
        mainPageMap.put("photography", convertListToMainPageDTOList(photographyProductListEntity));
        List<ProductEntity> typographyProductListEntity
                = productRepository.findTop6ByCategoryOrderByCreatedAt("typography",PageRequest.of(0, 6));
        mainPageMap.put("typography", convertListToMainPageDTOList(typographyProductListEntity));
        return ResponseEntity.status(200).body(mainPageMap);
    }

    public ResponseEntity<ProductDetailDTO> findProductById(Long productId) {
        Optional<ProductEntity> byId = productRepository.findById(productId);
        ProductEntity productEntity = byId.get();
        List<ProductImageDto> productImageDtoList = new ArrayList<>();
        for (ProductImageEntity productImageEntity : productEntity.getProductImageEntity()){
            ProductImageDto imageDto = ProductImageDto
                    .builder()
                    .imageNum(productImageEntity.getImageNum())
                    .imagePath(productImageEntity.getImagePath())
                    .build();
            productImageDtoList.add(imageDto);
        }

        ProductDetailDTO productDetailDTO = ProductDetailDTO
                .builder()
                .productId(productEntity.getId())
                .productName(productEntity.getProductName())
                .productSize(productEntity.getProductSize())
                .productContents(productEntity.getProductContents())
                .productPrice(productEntity.getProductPrice())
                .productImages(productImageDtoList)
                .build();
        return ResponseEntity.status(200).body(productDetailDTO);
    }

    public ResponseEntity<Page<ProductListDTO>> findProductByKeyword(String keyword, int size, int page, String sort) {
        Pageable pageable = setPageable(size, page, sort);
        Page<ProductEntity> productList = productRepository.findByProductNameContaining(pageable, keyword);
        return ResponseEntity.status(200).body(convertPageToDTOList(productList));
    }

    public ResponseEntity<Page<ProductListDTO>> findBestProductList(int size, int page) {
        Page<ProductEntity> productList;
        Pageable pageable = PageRequest.of(page, size);
        productList = productRepository.findTop30ByOrderBySellAmountDesc(pageable);
        return ResponseEntity.status(200).body(convertPageToDTOList(productList));
    }

    public ResponseEntity<Page<ProductListDTO>> findProductListByCategory(String category, int size, int page, String sort) {
        Pageable pageable = setPageable(size, page, sort);
        Page<ProductEntity> productList;
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


public Page<ProductListDTO> convertPageToDTOList(Page<ProductEntity> page) {
    List<ProductListDTO> productListDTOList = page.getContent()
            .stream()
            .map(this::convertToProductListDTO)
            .collect(Collectors.toList());

    return new PageImpl<>(productListDTOList, page.getPageable(), page.getTotalElements());
}

    public ProductListDTO convertToProductListDTO(ProductEntity productEntity) {
        String url = String.valueOf(productRepository.findById(productEntity.getId()).get().getProductImageEntity().get(0).getImagePath());
        ProductListDTO dto = ProductListDTO
                .builder()
                .productId(productEntity.getId())
                .productName(productEntity.getProductName())
                .productPrice(productEntity.getProductPrice())
                .imageUrl(url)
                .stockAmount(productEntity.getStock().getStockAmount())
                .build();
        return dto;
    }

    public List<MainPageDTO> convertListToMainPageDTOList(List<ProductEntity> list) {
        return list
                .stream()
                .map(this::convertToMainPageDTO)
                .collect(Collectors.toList());
    }

    public MainPageDTO convertToMainPageDTO(ProductEntity productEntity){
        String url = String.valueOf(productRepository.findById(productEntity.getId()).get().getProductImageEntity().get(0).getImagePath());
        MainPageDTO dto = MainPageDTO
                .builder()
                .productId(productEntity.getId())
                .imageUrl(url)
                .build();
        return dto;
    }


}
