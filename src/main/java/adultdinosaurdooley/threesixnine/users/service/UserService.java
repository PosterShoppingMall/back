package adultdinosaurdooley.threesixnine.users.service;

import adultdinosaurdooley.threesixnine.users.entity.User;
import adultdinosaurdooley.threesixnine.users.service.exception.UserErrorCode;
import adultdinosaurdooley.threesixnine.users.service.exception.UserException;
import adultdinosaurdooley.threesixnine.users.dto.MyPage;
import adultdinosaurdooley.threesixnine.users.dto.UpdateMyPage;
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

//    public User findByUserId(Long id){
//        return userRepository.findById(id).get();
//    }

    public MyPage getMyPage(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return MyPage.fromEntity(user);
    }

    @Transactional
    public String updateMyPage(Long userId, UpdateMyPage updateMyPage) {

        validatedPhoneNumber(updateMyPage.getPhoneNumber());
        validatedPassword(updateMyPage.getPassword());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));

//        //기존에 있던 파일 삭제
//        if(user.getImageUrl() != null) {
//            s3Service.deleteFile(user.getImageUrl());
//        }
//        //사진 업로드
//        String image = s3Service.uploadFile(multipartFile);
//
//        user.setImageUrl(image);

        User.update(user,updateMyPage);
        return "회원수정 완료";
    }

    public void validatedPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            throw new UserException(UserErrorCode.INVALID_PHONE_NUMBER);
        }
    }

    public void validatedPassword(String password) {
        if (password.length() < 8) {
            throw new UserException(UserErrorCode.INVALID_PASSWORD);
        }
        if (!password.matches(
                "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$")) {
            throw new UserException(UserErrorCode.INVALID_PHONE_NUMBER_PATTERN);
        }
    }
}
