package com.Purrrfect.Security;

import com.Purrrfect.Model.User;
import com.Purrrfect.Repo.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepo userRepo;
    private final JwtTokenProvider jwtTokenProvider;

    public OAuth2SuccessHandler(UserRepo userRepo,
                                     JwtTokenProvider jwtTokenProvider) {
        this.userRepo = userRepo;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String picture = oauthUser.getAttribute("picture");
        String providerId = oauthUser.getAttribute("sub");
        Boolean emailVerified = oauthUser.getAttribute("email_verified");

        Optional<User> existingUser = userRepo.findByEmail(email);

        User user = existingUser.orElseGet(() -> {
            User u = new User();
            u.setEmail(email);
            u.setName(name);
            u.setUsername(email);
            u.setProvider("GOOGLE");
            u.setProviderId(providerId);
            u.setPicture(picture);
            u.setEmailVerified(emailVerified != null && emailVerified);
            u.setPassword("OAUTH2_USER");
            u.setPhoneNumber("0000000000"); // temp
            return userRepo.save(u);
        });

        String token = jwtTokenProvider.generateToken(
                user.getEmail(),
                new String[]{"ROLE_USER"},
                user.getName(),
                user.getPicture()
        );

        String redirectUrl =
                "http://localhost:5173/oauth2/redirect?token=" + token;

        response.sendRedirect(redirectUrl);
    }
}
