package adultdinosaurdooley.threesixnine.user.jwt;

import adultdinosaurdooley.threesixnine.user.dto.LoginTokenSaveDto;
import adultdinosaurdooley.threesixnine.user.dto.Token;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final CustomUserDetailsService customUserDetailsService;

    @Value("${app.auth.tokenSecret}")
    private String secretKey;

    @Value("${app.auth.tokenExpiry}")
    private Long tokenValidTime;

    @Value("${app.auth.refreshTokenExpiry}")
    private Long refreshTokenValidTime;


    // 객체 초기화, secretKey를 Base64로 인코딩
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private String ROLE = "role";

    // JWT 토큰 생성
    public Token createToken(Long userPK, LoginTokenSaveDto loginTokenSaveDto) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPK)); // JWT payload에 저장되는 정보 단위
        claims.put(ROLE, loginTokenSaveDto); // 정보 저장 (key-value)
        Date now = new Date();
        Date tokenExpiryCalc = new Date(now.getTime() + tokenValidTime);
        Date refreshTokenExpiryCalc = new Date(now.getTime() + refreshTokenValidTime);

        String accessToken = Jwts.builder()
                                 .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더부분 세팅
                                 .setClaims(claims)
                                 .setIssuedAt(now)
                                 .setExpiration(tokenExpiryCalc) // 페이로드 세팅
                                 .signWith(SignatureAlgorithm.HS256, secretKey) // 시그니처 세팅
                                 .compact();

        String refreshToken = Jwts.builder()
                                  .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                                  .setIssuedAt(now)
                                  .setExpiration(refreshTokenExpiryCalc)
                                  .signWith(SignatureAlgorithm.HS256, secretKey)
                                  .compact();

        return Token.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .accessTokenExpireDate(tokenExpiryCalc)
                    .build();
    }

    // JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = claims(token);
        org.springframework.security.core.userdetails.UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Claims claims(String token){
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    // 토큰에서 회원 정보 추출
    public String getUserPK(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴. "X-AUTH-TOKEN": "TOKEN 값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
