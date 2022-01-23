package kirill.ecommerce.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${ecommerce.kirill.jwtSecret}")
    private String JWT_SECRET;
    private static final int JWT_VALIDITY = 500000000;

    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String generateToken(String username){
        return Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + JWT_VALIDITY))
                .setSubject(username).signWith(SignatureAlgorithm.HS512, JWT_SECRET).compact();
    }

    public String parseTokenFromRequest(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        if(StringUtils.hasText(header) && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }

    public String parseUsername(String authToken){
        return Jwts.parser().setSigningKey(JWT_SECRET)
               .parseClaimsJws(authToken).getBody().getSubject();
//        try{
//            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws()
//        }
    }
}
