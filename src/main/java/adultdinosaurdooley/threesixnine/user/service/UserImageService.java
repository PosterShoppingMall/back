package adultdinosaurdooley.threesixnine.user.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public Map<String, String> saveImg(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID() + "-" + originalFilename;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getInputStream().available());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        String accessUrl = amazonS3Client.getUrl(bucket, fileName).toString();
        Map<String, String> result = new HashMap<>();
        result.put("originalFilename", originalFilename);
        result.put("fileName", fileName);
        result.put("accessUrl", accessUrl);

        return result;
    }

    public String deleteImg(String fileName) {
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
        return "이미지 삭제";
    }
}
