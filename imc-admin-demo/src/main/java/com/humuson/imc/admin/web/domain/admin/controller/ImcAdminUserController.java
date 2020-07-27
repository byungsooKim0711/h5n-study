package com.humuson.imc.admin.web.domain.admin.controller;

import com.humuson.imc.admin.security.ImcUserDetails;
import com.humuson.imc.admin.security.ImcUserDetailsService;
import com.humuson.imc.admin.web.domain.code.ImcGrantedAuthority;
import com.humuson.imc.admin.web.domain.user.WebUserAuthor;
import com.humuson.imc.admin.web.dto.ImcLoginUser;
import com.humuson.imc.admin.web.dto.WebAdminUserDto;
import com.humuson.imc.admin.web.dto.converter.ImcLoginUserConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * IMC 통합 어드민 관리자 컨트롤러
 *
 */

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImcAdminUserController {

    private final ImcUserDetailsService imcUserDetailsService;

    // 로그인 유저인지 세션 체크
    @GetMapping("/login/check")
    public ResponseEntity<ImcLoginUser> checkLoginSession(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null) {
//            log.info("Login check Fail. Session is null");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(ImcGrantedAuthority.DASHBOARD.getRole()))) {
//            log.info("Login check fail. Not includes granted-authority: {}", authentication.getAuthorities());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ImcUserDetails details = (ImcUserDetails) authentication.getPrincipal();
        ImcLoginUser loginUser = new ImcLoginUserConverter().toDto(details);

        log.info("Login check success. username: {}, granted-authorities: {}", loginUser.getUser().getUserLogin(), loginUser.getAuthorities());
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    // 내 정보 수정
    @Secured("ROLE_USER")
    @PostMapping("/myinfo/{id}")
    public ResponseEntity<?> modifyMyInfo(@RequestBody WebAdminUserDto dto, @PathVariable long id) throws Exception {
        // TODO:
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 관리자 목록 조회
    @Secured("ROLE_MANAGE")
    @GetMapping("/admin")
    public ResponseEntity<List<WebAdminUserDto>> getWebAdminUserList() throws Exception {
        List<WebAdminUserDto> admins = imcUserDetailsService.selectAllWebAdminUser();

        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    // 관리자 추가
    @Secured("ROLE_MANAGE")
    @PostMapping("/admin")
    public ResponseEntity<WebAdminUserDto> addWebAdminUser(@RequestBody WebAdminUserDto user, UriComponentsBuilder uriBuilder) throws Exception {
        WebAdminUserDto created = imcUserDetailsService.insertWebAdminUser(user);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/admin/{id}").buildAndExpand(created.getId()).toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    // 관리자 수정
    @Secured("ROLE_MANAGE")
    @PutMapping("/admin/{id}")
    public ResponseEntity<WebAdminUserDto> modifyWebAdminUser(@RequestBody WebAdminUserDto webAdminUser, @PathVariable Long id) throws Exception {
        WebAdminUserDto updated = imcUserDetailsService.updateWebAdminUser(webAdminUser, id);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // 권한 리스트 조회
    @Secured("ROLE_MANAGE")
    @GetMapping("/admin/author")
    public ResponseEntity<List<WebUserAuthor>> getWebUserAuthorList() throws Exception {
        List<WebUserAuthor> authors = imcUserDetailsService.selectAllWebUserAuthor();

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

}
