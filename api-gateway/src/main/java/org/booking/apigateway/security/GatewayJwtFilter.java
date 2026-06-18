package org.booking.apigateway.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.booking.apigateway.exception.JwtEntryPoint;
import org.booking.apigateway.exception.custom.AuthenticationTokenException;
import org.booking.apigateway.security.dto.JwtUserInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class GatewayJwtFilter extends OncePerRequestFilter {

    private final JwtTokenParser parser;
    private final JwtEntryPoint jwtEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();

        if (path.startsWith("/api/auth/login") || path.startsWith("/api/auth/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            jwtEntryPoint.commence(request,response, new AuthenticationTokenException("Missing Authorization header in request"));
            return;
        }

        String token = header.substring(7);

        if (!parser.isValid(token)) {
            jwtEntryPoint.commence(request,response, new AuthenticationTokenException("Invalid token"));
            return;
        }

        JwtUserInfo user = parser.parse(token);

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(user, null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.role()))
                );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);

        MutableRequest wrapped = new MutableRequest(request);
        wrapped.addHeader("X-User-Id", user.userId());
        wrapped.addHeader("X-User-Email", user.email());
        wrapped.addHeader("X-User-Role", user.role());

        filterChain.doFilter(wrapped, response);
    }
}
