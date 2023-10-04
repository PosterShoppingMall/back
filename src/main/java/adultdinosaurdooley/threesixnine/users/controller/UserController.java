package adultdinosaurdooley.threesixnine.users.controller;

import adultdinosaurdooley.threesixnine.users.dto.UpdateMyPage;
import adultdinosaurdooley.threesixnine.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/369")
@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getMyPage(@PathVariable("userId") Long userId){
//        Long userId = 1L;
        return ResponseEntity.ok(userService.getMyPage(userId));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object>updateMyPage(@PathVariable("id") Long userId,@RequestPart UpdateMyPage updateMyPage){
//        Long userId = 1L;
        return ResponseEntity.ok(userService.updateMyPage(userId,updateMyPage));
    }
}
