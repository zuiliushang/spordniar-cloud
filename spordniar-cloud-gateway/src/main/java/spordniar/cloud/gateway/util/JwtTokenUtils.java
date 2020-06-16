package spordniar.cloud.gateway.util;

import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtils.class);
	
	private static final String secret = "spordniar";
	
	public static String createToken(TokenDetail tokenDetail) {
		return Jwts.builder().setExpiration(new Date(tokenDetail.getExptime()))
				.setSubject(tokenDetail.getUsername())
				.claim("username", tokenDetail.getUsername())
				.claim("role", tokenDetail.getAuthList())
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
	public static TokenDetail parse(String token) {
		LOGGER.info("token = {}", token);
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		TokenDetail detail = new TokenDetail();
		detail.setId(NumberUtils.toLong(claims.getId()));
		detail.setUsername(claims.get("username", String.class));
		detail.setExptime(claims.getExpiration() == null ? null : claims.getExpiration().getTime());
		LOGGER.debug("Token Detail: {}", detail);
		return detail;
	}
	
}
