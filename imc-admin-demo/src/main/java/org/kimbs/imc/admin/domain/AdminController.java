package org.kimbs.imc.admin.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.imc.admin.security.ImcUserDetailsService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminController {

    private final ImcUserDetailsService imcUserDetailsService;

    @PostMapping("/admin")
    public WebAdminUser addWebAdminUser(@RequestBody WebAdminUser user) {
        return imcUserDetailsService.insertWebAdminUser(user);
    }

    @PostMapping("/admin/{id}")
    public WebAdminUser modifyWebAdminUser(@RequestBody WebAdminUser webAdminUser, @PathVariable Long id) {
        return imcUserDetailsService.updateWebAdminUser(webAdminUser, id);
    }

    @DeleteMapping("/admin/{id}")
    public WebAdminUser removeWebAdminUser(@PathVariable Long id) {
        return imcUserDetailsService.deleteWebAdminUser(id);
    }
}
