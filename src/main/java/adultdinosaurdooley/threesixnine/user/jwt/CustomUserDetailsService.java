package adultdinosaurdooley.threesixnine.user.jwt;

import adultdinosaurdooley.threesixnine.user.entity.UserEntity;
import adultdinosaurdooley.threesixnine.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    public UserDetails loadUserByUsername(String userPK) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(Long.valueOf(userPK)).get();
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setID(String.valueOf(userEntity.getId()));
        customUserDetails.setNAME(userEntity.getEmail());
        customUserDetails.setAUTHORITY(String.valueOf(userEntity.getRole()));
        return customUserDetails;
    }

}
