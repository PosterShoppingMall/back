package adultdinosaurdooley.threesixnine.product.service;

import adultdinosaurdooley.threesixnine.product.entity.Product;
import adultdinosaurdooley.threesixnine.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    // 특정 상품 조회
    public Product productView(Long id){
        return productRepository.findById(id).get();
    }
}
