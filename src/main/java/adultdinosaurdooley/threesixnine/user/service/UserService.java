package adultdinosaurdooley.threesixnine.user.service;


import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.service.exception.UserErrorCode;
import adultdinosaurdooley.threesixnine.user.service.exception.UserException;
import adultdinosaurdooley.threesixnine.user.dto.MyPageDTO;
import adultdinosaurdooley.threesixnine.user.dto.UpdateMyPageDTO;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
//    private final UserImageService userImageService;


    public UserEntity findByUserId(Long id){
        return userRepository.findById(id).get();
    }

    public MyPageDTO getMyPage(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return MyPageDTO.fromEntity(user);
    }

    @Transactional
    public MyPageDTO updateMyPage(Long userId, UpdateMyPageDTO updateMyPage) {

        validatedPhoneNumber(updateMyPage.getPhoneNumber());
//        validatedPassword(updateMyPage.getPassword());

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_AUTHORIZED));

//        //기존에 있던 파일 삭제
//        if(user.getUserImg() != null) {
//            s3Service.deleteFile(user.getUserImg());
//        }
//        //사진 업로드
//        String image = s3Service.upload(multipartFile);
//
//        user.setUserImg(image);

        UserEntity.update(user,updateMyPage);
        return MyPageDTO.fromEntity(user);
    }

    public void validatedPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            throw new UserException(UserErrorCode.INVALID_PHONE_NUMBER);
        }
    }

//    public void validatedPassword(String password) {
//        if (password.length() < 8) {
//            throw new UserException(UserErrorCode.INVALID_PASSWORD);
//        }
//        if (!password.matches(
//                "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,}$")) {
//            throw new UserException(UserErrorCode.INVALID_PHONE_NUMBER_PATTERN);
//        }
//    }
}
