package com.example.springbootoauth.auth;

import com.example.springbootoauth.auth.dto.AccessToken;
import com.example.springbootoauth.auth.dto.GithubUserInfo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class OAuthService {
    private final String clientId;
    private final String redirectUrl;
    private final String loginUrl;
    private final String clientSecret;
    private final String state;
    private final String tokenUrl;
    private final String userUrl;

    private static final String CLIENT_ID_PARAM = "client_id";
    private static final String REDIRECT_URL_PARAM = "redirect_url";
    private static final String CLIENT_SECRET_PARAM = "client_secret";
    private static final String CODE_PARAM = "code";
    private static final String STATE_PARAM = "state";

    public OAuthService(
            @Value("${oauth2.user.github.client-id}") String clientId,
            @Value("${oauth2.user.github.redirect-url}") String redirectUrl,
            @Value("${oauth2.user.github.login-url}") String loginUrl,
            @Value("${oauth2.user.github.client-secret}") String clientSecret,
            @Value("${oauth2.user.github.token-url}") String tokenUrl,
            @Value("${oauth2.user.github.user-url}") String userUrl) {

        this.clientId = clientId;
        this.redirectUrl = redirectUrl;
        this.loginUrl = loginUrl;
        this.state = UUID.randomUUID().toString();
        this.clientSecret = clientSecret;
        this.tokenUrl = tokenUrl;
        this.userUrl = userUrl;
    }

    public RedirectView requestCode(RedirectAttributes redirectAttributes) {
        addCommonAttributes(redirectAttributes);
        return new RedirectView(loginUrl);
    }

    public RedirectView requestToken(String code, String state, RedirectAttributes redirectAttributes) {
        addCommonAttributes(redirectAttributes);
        redirectAttributes.addAttribute(CODE_PARAM, code);
        return new RedirectView(tokenUrl);
    }

    private void addCommonAttributes(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute(CLIENT_ID_PARAM, clientId);
        redirectAttributes.addAttribute(REDIRECT_URL_PARAM, redirectUrl);
        redirectAttributes.addAttribute(CLIENT_SECRET_PARAM, clientSecret);
        redirectAttributes.addAttribute(STATE_PARAM, state);
    }

    public AccessToken getAccessToken(String code) {
        Map<String, String> bodies = new HashMap<>();
        bodies.put(CLIENT_ID_PARAM, clientId);
        bodies.put(CLIENT_SECRET_PARAM, clientSecret);
        bodies.put(CODE_PARAM, code);
        bodies.put(STATE_PARAM, state);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Object> request = new HttpEntity<>(bodies, headers);
        ResponseEntity<AccessToken> response = new RestTemplate().postForEntity(tokenUrl, request, AccessToken.class);

        return response.getBody();
    }

    public GithubUserInfo getGithubUserInfo(AccessToken accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", accessToken.getAccessToken()));

        HttpEntity<Object> request = new HttpEntity<>(headers);
        ResponseEntity<GithubUserInfo> response = new RestTemplate()
                .exchange(userUrl, HttpMethod.GET, request, GithubUserInfo.class);

        return response.getBody();
    }
}
