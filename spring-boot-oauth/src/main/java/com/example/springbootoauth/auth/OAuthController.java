package com.example.springbootoauth.auth;

import com.example.springbootoauth.auth.dto.AccessToken;
import com.example.springbootoauth.auth.dto.GithubUserInfo;
import com.example.springbootoauth.user.User;
import com.example.springbootoauth.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;

@RestController
public class OAuthController {
    private final OAuthService oAuthService;
    private final UserRepository userRepository;
    private static final String REDIRECT_URL = "http://localhost:8080";

    public OAuthController(OAuthService oAuthService, UserRepository userRepository) {
        this.oAuthService = oAuthService;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public ResponseEntity<String> home(HttpServletResponse response, HttpSession session) {
        GithubUserInfo checkSession = (GithubUserInfo) session.getAttribute("userInfo");
        System.out.println("session: " + session);
        if (checkSession != null) {
            System.out.println("checkSession: " + session.getAttribute("userInfo"));
        }

        //  첫 번째 검증 (세션이 존재하는가 ?)
        //  두 번째 검증 (Session 내부에 gitHubUserInfo 객체가 있지만, 내부 데이터를 확인해서 DB 와 일치하는 값이 있는가 ?)
        if ((checkSession == null) || userRepository.findByName(checkSession.getName()).isEmpty()) {
            try {
                response.sendRedirect("http://localhost:8080/loginPage");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok("Home Page 입니다.");
    }

    @GetMapping("/loginPage")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.ok("login page 입니다.");
    }

    @GetMapping("/login")
    public RedirectView getCode(RedirectAttributes redirectAttributes) {
        try {
            return oAuthService.requestCode(redirectAttributes);
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/loginError"); // 에러 페이지로 리다이렉트
        }
    }

    // Redirect URL에 담겨준 Authorization Code를 처리하고 AccessToken을 가지고 정보를 가져온다. 
    @GetMapping("/login/oauth")
    public ResponseEntity<HttpHeaders> getUserInfo(
            @RequestParam String code, @RequestParam String state, HttpServletRequest request) {
        AccessToken accessToken = oAuthService.getAccessToken(code);
        System.out.println("AccessToken 저장 완료 - " + accessToken);

        GithubUserInfo githubUserInfo = oAuthService.getGithubUserInfo(accessToken);
        System.out.println("User 정보 - " + githubUserInfo);

        HttpSession session = request.getSession();
        session.setAttribute("userInfo", githubUserInfo);

        return saveUserInfo(githubUserInfo);
    }

    private ResponseEntity<HttpHeaders> saveUserInfo(GithubUserInfo githubUserInfo) {
        if (githubUserInfo.getName() == null || githubUserInfo.getName().isEmpty()) {
            System.out.println("Username 이 없다." + githubUserInfo.getName());
        }

        User user = new User(githubUserInfo);
        userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(REDIRECT_URL));
        return new ResponseEntity<>(headers, HttpStatus.SEE_OTHER);
    }
}
