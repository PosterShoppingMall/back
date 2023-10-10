package adultdinosaurdooley.threesixnine.user.controller;

import adultdinosaurdooley.threesixnine.user.dto.UpdateMyPageDTO;
import adultdinosaurdooley.threesixnine.user.dto.UserDTO;
import adultdinosaurdooley.threesixnine.user.jwt.JwtTokenProvider;
import adultdinosaurdooley.threesixnine.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/369/user")
@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@ModelAttribute UserDTO userDTO) throws IOException {
        return userService.signup(userDTO);
    }

    @PostMapping("/emailcheck")
    public ResponseEntity<?> emailCheck(@RequestBody Map<String, String> email) {
        String userEmail = email.get("email");
        boolean emailCheck = userService.findByEmail(userEmail);
        return ResponseEntity.status(200).body(emailCheck);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> login) {
        return userService.login(login);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String header = request.getHeader("X-AUTH-TOKEN");
        String userId = jwtTokenProvider.getUserPK(header);
        return userService.logout(userId);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(HttpServletRequest request) {
        String header = request.getHeader("X-AUTH-TOKEN");
        String userid = jwtTokenProvider.getUserPK(header);
        return userService.delete(userid);
    }
    @GetMapping("/{user_id}")
    public ResponseEntity<Object> getMyPage(@PathVariable("user_id") Long userId,HttpServletRequest request){
        String header = request.getHeader("X-AUTH-TOKEN");
        String userid = jwtTokenProvider.getUserPK(header);

        // userId와 userid가 일치하는지 확인
        if (!userId.toString().equals(userid)) {
            // userId와 userid가 일치하지 않으면 권한이 없다는 응답을 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }

        return ResponseEntity.ok(userService.getMyPage(userId,userid));
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<Object>updateMyPage(@PathVariable("user_id") Long userId,@ModelAttribute UpdateMyPageDTO updateMyPage,HttpServletRequest request) throws IOException {
        String header = request.getHeader("X-AUTH-TOKEN");
        String userid = jwtTokenProvider.getUserPK(header);

        // userId와 userid가 일치하는지 확인
        if (!userId.toString().equals(userid)) {
            // userId와 userid가 일치하지 않으면 권한이 없다는 응답을 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("권한이 없습니다.");
        }

        return ResponseEntity.ok(userService.updateMyPage(userId,updateMyPage,userid));
    }
}
