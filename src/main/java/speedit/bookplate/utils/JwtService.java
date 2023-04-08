package speedit.bookplate.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import speedit.bookplate.config.secret.Secret;
import speedit.bookplate.exception.EmptyTokenException;
import speedit.bookplate.exception.ExpireTokenException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class JwtService {

    private final Long tokenSeconds = 1000L * 60 * 60;

    /*
    액세스 토큰 생성
    @param userIdx
    @return String
     */
    public String createAccessToken(long userIdx){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type", "jwt")
                .claim("userIdx", userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + tokenSeconds))
                .signWith(SignatureAlgorithm.HS256, Secret.JWT_SECRET_KEY)
                .compact();
    }

    /*
    리프래시 토큰 생성
    @param userIdx
    @return String
     */
    public String createRefreshToken(long userIdx){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam("type","jwt")
                .claim("userIdx",userIdx)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() +tokenSeconds*72))
                .signWith(SignatureAlgorithm.HS256,Secret.JWT_SECRET_KEY)
                .compact();
    }

    /*
    Header에서 ACCESS-TOKEN으로 JWT 추출
    @return String
     */
    public String getAccessToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("ACCESS-TOKEN");
    }

    public String getRefreshToken(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("REFRESH-TOKEN");
    }

    public boolean isExpireRefreshToken(){
        String refreshToken = getRefreshToken();

        if(refreshToken == null || refreshToken.length() == 0){
            throw new EmptyTokenException();
        }

        try {
            Jws<Claims> claims;
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(refreshToken);
            return claims.getBody().getExpiration().before(new Date());
        } catch (Exception ignored){
            throw new ExpireTokenException();
        }
    }

    public boolean isExpireAccessToken(){
        //1.JWT 추출
        String accessToken = getAccessToken();

        if (accessToken == null || accessToken.length() == 0) {
            throw new EmptyTokenException();
        }

        //2.JWT parsing
        Jws<Claims> claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(Secret.JWT_SECRET_KEY)
                    .parseClaimsJws(accessToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception ignored) {
            throw new ExpireTokenException();
        }
    }

    public Long getUserIdx(){
        String accessToken = getAccessToken();

        Jws<Claims> claims;
        claims = Jwts.parser()
                .setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(accessToken);

        return claims.getBody().get("userIdx",Long.class);
    }

    public Long getUserIdxUsingRefreshToken(){
        String refreshToken = getRefreshToken();

        Jws<Claims> claims;
        claims = Jwts.parser()
                .setSigningKey(Secret.JWT_SECRET_KEY)
                .parseClaimsJws(refreshToken);

        return claims.getBody().get("userIdx",Long.class);
    }

}
