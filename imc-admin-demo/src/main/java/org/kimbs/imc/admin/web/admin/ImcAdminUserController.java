package org.kimbs.imc.admin.web.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.imc.admin.domain.WebAdminUser;
import org.kimbs.imc.admin.domain.WebUserAuthor;
import org.kimbs.imc.admin.domain.code.ImcGrantedAuthority;
import org.kimbs.imc.admin.security.ImcUserDetails;
import org.kimbs.imc.admin.security.ImcUserDetailsService;
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

    @GetMapping("/login/check")
    public ResponseEntity<?> checkLoginSession(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(ImcGrantedAuthority.USER.getRole()))) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        ImcUserDetails account = (ImcUserDetails) authentication.getPrincipal();

        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @Secured("ROLE_MANAGE")
    @GetMapping("/admin")
    public ResponseEntity<List<WebAdminUser>> getWebAdminUserList() throws Exception {
        List<WebAdminUser> admins = imcUserDetailsService.selectAllWebAdminUser();
        admins.forEach(admin -> admin.setPassword(""));

        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @Secured("ROLE_MANAGE")
    @PostMapping("/admin")
    public ResponseEntity<WebAdminUser> addWebAdminUser(@RequestBody WebAdminUser user, UriComponentsBuilder uriBuilder) throws Exception {
        WebAdminUser created = imcUserDetailsService.insertWebAdminUser(user);
        created.setPassword("");

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/admin/{id}").buildAndExpand(created.getId()).toUri());

        return new ResponseEntity<>(created, headers, HttpStatus.CREATED);
    }

//    @PreAuthorize("hasRole('ROLE_MANAGE')")
    @Secured("ROLE_MANAGE")
    @PutMapping("/admin/{id}")
    public ResponseEntity<WebAdminUser> modifyWebAdminUser(@RequestBody WebAdminUser webAdminUser, @PathVariable Long id) throws Exception {
        WebAdminUser updated = imcUserDetailsService.updateWebAdminUser(webAdminUser, id);
        updated.setPassword("");

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @Secured("ROLE_MANAGE")
    @GetMapping("/admin/author")
    public ResponseEntity<List<WebUserAuthor>> getWebUserAuthorList() throws Exception {
        List<WebUserAuthor> authors = imcUserDetailsService.selectAllWebUserAuthor();

        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

}
