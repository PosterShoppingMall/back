//package adultdinosaurdooley.threesixnine.users.service;
//
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.CannedAccessControlList;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class S3Service {
//
//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
//
//    @Value("${cloud.aws.baseUrl}")
//    private String baseUrl;
//
//    private final AmazonS3 amazonS3;
//
//
//    // 이미지 파일 s3에 저장 후 Dto 리스트에 담아서 반환
//    public List<String> uploadFile(List<MultipartFile> multipartFile) {
//        List<String> reponseDto = new ArrayList<>();
//
//        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 reponseDto에 추가
//        multipartFile.forEach(file -> {
//            String fileName = createFileName(file.getOriginalFilename());
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(file.getSize());
//            objectMetadata.setContentType(file.getContentType());
//
//            try(InputStream inputStream = file.getInputStream()) {
//                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//                        .withCannedAcl(CannedAccessControlList.PublicRead));
//                log.info("a3 업로드 성공");
//            } catch(IOException e) {
//                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
//            }
//
//            // file에 관한 내용을 Dto로 변환 후 list에 담아 return
//            reponseDto.add(baseUrl + fileName);
//
//
//
//        });
//
//        return reponseDto;
//    }
//
//    public void deleteFile(String url) {
//        String fileName = url.replaceAll(baseUrl, "");
//        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
//    }
//
//    // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 UUID를 붙여준다.
//    private String createFileName(String fileName) {
//        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
//    }
//
//    // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였다.
//    private String getFileExtension(String fileName) {
//        try {
//            return fileName.substring(fileName.lastIndexOf("."));
//        } catch (StringIndexOutOfBoundsException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
//        }
//    }
//
//    public String uploadFile(MultipartFile multipartFile) {
//        if (multipartFile.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//
//        String fileName = createFileName(multipartFile.getOriginalFilename());
//        ObjectMetadata objectMetadata = new ObjectMetadata();
//        objectMetadata.setContentLength(multipartFile.getSize());
//        objectMetadata.setContentType(multipartFile.getContentType());
//
//        log.info("사진 {}", fileName);
//        try (InputStream inputStream = multipartFile.getInputStream()) {
//            amazonS3.putObject(
//                    new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
//                            .withCannedAcl(CannedAccessControlList.PublicRead));
//        } catch (IOException e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        }
//        return baseUrl + fileName;
//    }
//}
//
