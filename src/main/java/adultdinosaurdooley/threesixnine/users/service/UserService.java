package adultdinosaurdooley.threesixnine.users.service;

import adultdinosaurdooley.threesixnine.users.entity.User;
import adultdinosaurdooley.threesixnine.users.service.exceptions.NotFoundException;
import adultdinosaurdooley.threesixnine.users.dto.MyPage;
import adultdinosaurdooley.threesixnine.users.dto.UpdateMyPage;
import adultdinosaurdooley.threesixnine.users.entity.UserDetail;
import adultdinosaurdooley.threesixnine.users.repository.UserDetailRepository;
import adultdinosaurdooley.threesixnine.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;


    public User findUser(Long id){
        return userRepository.findById(id).get();
    }

    public MyPage getMyPage(Long userId) {
        UserDetail userDetail = userDetailRepository.findByUserId(userId);
        if (userId == null) {
            throw new NotFoundException("USER 를 찾을 수가 없습니다.");
        }
        return MyPage.fromEntity(userDetail);
    }

    @Transactional
    public String updateMyPage(Long userId, UpdateMyPage updateMyPage) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException("USER 를 찾을 수가 없습니다."));

//        //기존에 있던 파일 삭제
//        if(user.getImageUrl() != null) {
//            s3Service.deleteFile(user.getImageUrl());
//        }
//        //사진 업로드
//        String image = s3Service.uploadFile(multipartFile);
//
//        user.setImageUrl(image);

        UserDetail userDetail = userDetailRepository.findByUserId(userId);//회원의 ID로 접근 권한 처리

        UserDetail.update(userDetail,updateMyPage);

        User.update(user,updateMyPage);
        return "회원수정이 성공적으로 완료되었습니다";
    }
}
