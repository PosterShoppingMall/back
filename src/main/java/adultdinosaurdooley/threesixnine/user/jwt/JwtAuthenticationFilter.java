package adultdinosaurdooley.threesixnine.user.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
// 토큰 검증 필터 클래스
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {

        // HTTP 요청으로부터 토큰을 가져옴
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

//        // 토큰이 존재하고 유효하면 인증 정보를 가져와서 보안 컨텍스트에 설정
        if(token!= null && jwtTokenProvider.validateToken(token)){
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 필터 체인을 계속 진행
        filterChain.doFilter(request,response);
    }
}
