package org.kimbs.imc.admin.web.user;

import lombok.RequiredArgsConstructor;
import org.kimbs.imc.admin.domain.WebUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImcUserController {

    private final ImcUserService imcUserService;

    @GetMapping("/user")
    public ResponseEntity<List<WebUser>> getImcUserList() throws Exception {
        List<WebUser> userList = imcUserService.getWebUserList();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
