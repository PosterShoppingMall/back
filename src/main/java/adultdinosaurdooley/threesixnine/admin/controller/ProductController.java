package adultdinosaurdooley.threesixnine.admin.controller;

import adultdinosaurdooley.threesixnine.admin.dto.ProductDTO;
import adultdinosaurdooley.threesixnine.admin.dto.UpdateProductDTO;
import adultdinosaurdooley.threesixnine.admin.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/369/admin")//공통 주소
public class ProductController {

    private final ProductService productService;


    //상품 등록
    @PostMapping(consumes = {"multipart/form-data"} )
    public ResponseEntity<Map<String, String>> registerProducts(@RequestPart(name = "file") List<MultipartFile> multipartFilelist,
                                                                @RequestPart (value = "data") ProductDTO productDto) throws IOException{

        System.out.println("productRequestDto " + productDto);
        return productService.resisterProducts(multipartFilelist, productDto);
    }


    //상품 전체 조회
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts(){
        //return productService.findAll();
        //상품 전체 조회(썸네일만)
        return productService.findAllByThumbnail();
    }




    //상품 수정
    @PutMapping (value = "{product_id}" ,consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> modifyProduct(@PathVariable Long product_id,
                                                             @RequestPart(name = "file") List<MultipartFile> multipartFilelist,
                                                             @RequestPart (value = "data") UpdateProductDTO updateProductDTO)throws IOException{
        return productService.updateProduct(multipartFilelist,  product_id ,updateProductDTO);
    }


}
