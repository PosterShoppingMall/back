package adultdinosaurdooley.threesixnine.users.controller;

import adultdinosaurdooley.threesixnine.users.dto.UpdateMyPageDto;
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

    @GetMapping("/{user_id}")
    public ResponseEntity<Object> getMyPage(@PathVariable("user_id") Long userId){
//        Long userId = 1L;
        return ResponseEntity.ok(userService.getMyPage(userId));
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<Object>updateMyPage(@PathVariable("user_id") Long userId,@RequestBody UpdateMyPageDto updateMyPage){
//        Long userId = 1L;
        return ResponseEntity.ok(userService.updateMyPage(userId,updateMyPage));
    }
}
