package org.kimbs.imc.admin.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.kimbs.imc.admin.security.ImcUserDetails;
import org.kimbs.imc.admin.security.ImcUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * IMC 통합 어드민 관리자 컨트롤러
 *
 */

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImcAdminUserController {

    private final ImcUserDetailsService imcUserDetailsService;

    // Vue.js 에서 세션 확인용으로 사용. (Custom Session Repository 적용 이후)
    @GetMapping("/login/check")
    public Object checkLoginSession(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new Exception("User is not login");
        }

        System.out.println(session.getId());
        Enumeration<String> names = session.getAttributeNames();
        while(names.hasMoreElements()) {
            System.out.println(names.nextElement());
        }

        System.out.println(session.getCreationTime());
        System.out.println(session.getLastAccessedTime());
        System.out.println(session.getMaxInactiveInterval());
        System.out.println(session.getServletContext());
        System.out.println(session.isNew());
        System.out.println(session);

        ImcUserDetails account = (ImcUserDetails) session.getAttribute("account");
        if (account == null) {
            throw new Exception("User is not login");
        }

        return account;
    }

    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @PostMapping("/admin")
    public ResponseEntity<WebAdminUser> addWebAdminUser(@RequestBody WebAdminUser user, UriComponentsBuilder uriBuilder) throws Exception{
        WebAdminUser created = imcUserDetailsService.insertWebAdminUser(user);
        created.setPassword("");

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/admin/{id}").buildAndExpand(created.getId()).toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @PutMapping("/admin/{id}")
    public ResponseEntity<WebAdminUser> modifyWebAdminUser(@RequestBody WebAdminUser webAdminUser, @PathVariable Long id) throws Exception {
        WebAdminUser updated = imcUserDetailsService.updateWebAdminUser(webAdminUser, id);
        updated.setPassword("");

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

}
