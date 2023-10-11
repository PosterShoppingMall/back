package adultdinosaurdooley.threesixnine.user.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class CustomUserDetails implements UserDetails {
    private String ID;
    private String PASSWORD;
    private String NAME;
    private String AUTHORITY;
    private boolean ENABLED;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(AUTHORITY));
        return auth;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getUsername() {
        return ID;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료되지 않았는지 리턴 (true: 만료 안됨)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠겨있지 않은지 리턴 (ture: 잠기지 않음)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료되지 않았는지 리턴 (true: 만료 안됨)
    }

    @Override
    public boolean isEnabled() {
        return ENABLED; // 계정 활성화인지 리턴
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String name) {
        NAME = name;
    }
}
