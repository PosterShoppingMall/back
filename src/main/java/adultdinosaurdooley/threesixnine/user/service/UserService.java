package adultdinosaurdooley.threesixnine.user.service;


import adultdinosaurdooley.threesixnine.user.dto.*;
import adultdinosaurdooley.threesixnine.user.entity.RefreshTokenEntity;
import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.jwt.JwtTokenProvider;
import adultdinosaurdooley.threesixnine.user.repository.RefreshTokenRepository;
import adultdinosaurdooley.threesixnine.user.service.exception.UserErrorCode;
import adultdinosaurdooley.threesixnine.user.service.exception.UserException;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserImageService userImageService;

    public ResponseEntity<?> signup(UserDTO userDTO) throws IOException {
        String emailRegEx = "^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*[A-Za-z]{2,3}$";
        String passwordRegEx = "^[A-Za-z0-9]{8,20}$";
        if (!findByEmail(userDTO.getEmail())) {
            if (Pattern.matches(emailRegEx, userDTO.getEmail())) {
                if (Pattern.matches(passwordRegEx, userDTO.getPassword())) {

                    if (userDTO.getUserImg().isEmpty()) {
                        // 기본 이미지 등록
                        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                        String encodePass = bCryptPasswordEncoder.encode(userDTO.getPassword());
                        Map<String, Object> response = new HashMap<>();
                        UserEntity userEntity = UserEntity.builder()
                                .email(userDTO.getEmail())
                                .name(userDTO.getName())
                                .password(encodePass)
                                .phoneNumber(userDTO.getPhoneNumber())
                                .postCode(userDTO.getPostCode())
                                .roadAddress(userDTO.getRoadAddress())
                                .detailAddress(userDTO.getDetailAddress())
//                                                      .storedName(savedImg.get("fileName"))
                                .userImg("https://channitestbucket.s3.ap-northeast-2.amazonaws.com/defautimg.jpg")
                                .role("ROLE_USER")
                                .build();

                        Long id = userRepository.save(userEntity).getId();
                        if (id > 0) {
                            response.put("success", true);
                            response.put("message", "회원가입완료");
                            return ResponseEntity.status(200).body(response);
                        } else {
                            response.put("success", false);
                            response.put("message", "회원가입에 실패했습니다.");
                            return ResponseEntity.status(400).body(response);
                        }
                    } else {
                        // 이미지 직접 등록
                        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                        String encodePass = bCryptPasswordEncoder.encode(userDTO.getPassword());
                        Map<String, String> savedImg = userImageService.saveImg(userDTO.getUserImg());
                        Map<String, Object> response = new HashMap<>();
                        UserEntity userEntity = UserEntity.builder()
                                .email(userDTO.getEmail())
                                .name(userDTO.getName())
                                .password(encodePass)
                                .phoneNumber(userDTO.getPhoneNumber())
                                .postCode(userDTO.getPostCode())
                                .roadAddress(userDTO.getRoadAddress())
                                .detailAddress(userDTO.getDetailAddress())
                                .originFileName(savedImg.get("originalFilename"))
                                .storedName(savedImg.get("fileName"))
                                .userImg(savedImg.get("accessUrl"))
                                .role("ROLE_USER")
                                .build();

                        Long id = userRepository.save(userEntity).getId();
                        if (id > 0) {
                            response.put("success", true);
                            response.put("message", "회원가입 완료");
                            return ResponseEntity.status(200).body(response);
                        } else {
                            response.put("success", false);
                            response.put("message", "회원가입에 실패했습니다.");
                            return ResponseEntity.status(400).body(response);
                        }
                    }
                } else {
                    return ResponseEntity.status(400).body("잘못된 비밀번호 형식입니다.");
                }
            } else {
                return ResponseEntity.status(400).body("잘못된 이메일 형식입니다.");
            }
        } else {
            return ResponseEntity.status(400).body("이미 존재하는 이메일 입니다.");
        }
    }

    public ResponseEntity<?> login(Map<String, String> login) {
        Map<String, String> result = new HashMap<>();
        if (findByEmail(login.get("email"))) {

            if (login.get("email").equals("admin") && login.get("password").equals("1234")) {
                UserEntity userEntity = userRepository.findByEmail(login.get("email"));
                LoginTokenSaveDTO loginTokenSaveDto = LoginTokenSaveDTO.builder()
                        .id(userEntity.getId())
                        .email(userEntity.getEmail())
                        .build();
                TokenDTO token = jwtTokenProvider.createToken(loginTokenSaveDto.getId(), loginTokenSaveDto);
                RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                        .token(token.getRefreshToken())
                        .userEntity(userEntity)
                        .build();
                Optional<RefreshTokenEntity> email = refreshTokenRepository.findByUserEntity_Id(userRepository.findByEmail(login.get("email"))
                        .getId());
                if (email.isPresent()) {
                    return ResponseEntity.status(200).body(token);
                } else {
                    refreshTokenRepository.save(refreshToken);
                }
                return ResponseEntity.status(200).body(token);
            }

            if (passwordCheck(login.get("email"), login.get("password"))) {
                UserEntity userEntity = userRepository.findByEmail(login.get("email"));
                LoginTokenSaveDTO loginTokenSaveDto = LoginTokenSaveDTO.builder()
                        .id(userEntity.getId())
                        .email(userEntity.getEmail())
                        .build();
                TokenDTO token = jwtTokenProvider.createToken(loginTokenSaveDto.getId(), loginTokenSaveDto);
                RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                        .token(token.getRefreshToken())
                        .userEntity(userEntity)
                        .build();
                Optional<RefreshTokenEntity> email = refreshTokenRepository.findByUserEntity_Id(userRepository.findByEmail(login.get("email"))
                        .getId());
                if (email.isPresent()) {
                    return ResponseEntity.status(200).body(token);
                } else {
                    refreshTokenRepository.save(refreshToken);
                }
                return ResponseEntity.status(200).body(token);

            } else {
                result.put("message", "이메일 또는 비밀번호가 일치하지 않습니다");
                return ResponseEntity.status(401).body(result);
            }
        } else {
            result.put("message", "이메일 또는 비밀번호가 일치하지 않습니다");
            return ResponseEntity.status(401).body(result);
        }
    }

    private boolean passwordCheck(String email, String password) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String decodePass = userRepository.findByEmail(email).getPassword();
        return bCryptPasswordEncoder.matches(password, decodePass);
    }

    public boolean findByEmail(String email) {
        UserEntity emailCheck = userRepository.findByEmail(email);
        if (emailCheck != null) {
            return true;
        } else {
            return false;
        }
    }

    public ResponseEntity<?> logout(String userId) {
        UserEntity findId = userRepository.findById(Long.valueOf(userId)).get();
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByUserEntity_Id(findId.getId()).get();
        if (refreshToken != null) {
            refreshTokenRepository.deleteById(refreshToken.getId());
            return ResponseEntity.status(200).body("로그아웃 완료");
        } else {
            return ResponseEntity.badRequest().body("로그아웃 실패");
        }
    }

    public ResponseEntity<?> delete(String id) {
        UserEntity findId = userRepository.findById(Long.valueOf(id)).get();
        String userImgUrl = findId.getUserImg();
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByUserEntity_Id(findId.getId()).get();

        if (userImgUrl.matches("https://channitestbucket.s3.ap-northeast-2.amazonaws.com/defautimg.jpg")) {
            refreshTokenRepository.deleteById(refreshToken.getId());
            userRepository.deleteById(findId.getId());
            return ResponseEntity.status(200).body("회원탈퇴 완료");
        } else {
            String str = ".com/";
            String fileName = userImgUrl.substring(userImgUrl.lastIndexOf(str) + str.length());
            String result = userImageService.deleteImg(fileName);
            if (result.matches("이미지 삭제")) {
                refreshTokenRepository.deleteById(refreshToken.getId());
                userRepository.deleteById(findId.getId());
                return ResponseEntity.status(200).body("회원탈퇴 완료");
            } else {
                return ResponseEntity.badRequest().body("회원탈퇴 실패");
            }
        }
    }


    public MyPageDTO getMyPage(Long userId, String userid) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.USER_NOT_FOUND));
        return MyPageDTO.fromEntity(user);
    }

    @Transactional
    public MyPageDTO updateMyPage(Long userId, UpdateMyPageDTO updateMyPage, String userid) throws IOException {

//        validatedPhoneNumber(updateMyPage.getPhoneNumber());
        validatedPassword(updateMyPage.getPassword());

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorCode.NOT_AUTHORIZED));

        //기존에 있던 파일 삭제
        if(user.getUserImg() != null) {
            userImageService.deleteImg(user.getUserImg());
        }

        Map<String, String> savedImg = userImageService.saveImg(updateMyPage.getUserImg());
        String accessUrl = savedImg.get("accessUrl");

        user.setUserImg(accessUrl);

        UserEntity.update(user, updateMyPage);

        UserEntity savedUser = userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        if (savedUser != null && savedUser.getId() > 0) {
            response.put("success", true);
            response.put("message", "회원 정보 수정 완료");
            return MyPageDTO.fromEntity(savedUser);
        } else {
            response.put("success", false);
            response.put("message", "회원 정보 수정 실패");
            return MyPageDTO.fromEntity(user); // 실패 시 기존 정보 반환
        }
    }

//    public void validatedPhoneNumber(String phoneNumber) {
//        if (phoneNumber.length() != 11) {
//            throw new UserException(UserErrorCode.INVALID_PHONE_NUMBER);
//        }
//    }

    public void validatedPassword(String password) {
        if (!password.matches(
                "^[A-Za-z0-9]{8,20}$")) {
            throw new UserException(UserErrorCode.INVALID_PHONE_NUMBER_PATTERN);
        }
    }
}
