package co.simplon.socworkbusiness.config;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtProvider {
    private final Algorithm algorithm;
    private final Long exp;
    private final String issuer;

    JwtProvider(Algorithm algorithm, Long exp, String issuer) {
        this.algorithm = algorithm;
        this.exp = exp;
        this.issuer = issuer;
    }

    public String create(String subject, List<String> roles) {
        Instant issuedAt = Instant.now();
        Builder builder = JWT.create().withIssuedAt(issuedAt).withSubject(subject).withIssuer(issuer).withClaim("roles",
                roles);

        if (exp > 0) {
            builder.withExpiresAt(new Date(System.currentTimeMillis() + exp));
        }
        return builder.sign(algorithm);
    }
}
