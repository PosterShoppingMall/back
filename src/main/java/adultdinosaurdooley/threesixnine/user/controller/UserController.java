package adultdinosaurdooley.threesixnine.user.controller;

import adultdinosaurdooley.threesixnine.user.dto.UserDTO;
import adultdinosaurdooley.threesixnine.user.jwt.JwtTokenProvider;
import adultdinosaurdooley.threesixnine.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/369/user")
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
}