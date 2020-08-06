package com.humuson.imc.admin.web.user;

import com.humuson.imc.admin.web.user.dto.WebUserConverter;
import com.humuson.imc.admin.web.user.dto.WebUserResponse;
import lombok.RequiredArgsConstructor;
import com.humuson.imc.admin.web.domain.user.WebUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ImcUserController {

    private final ImcUserService imcUserService;
    private final WebUserConverter converter;

    @GetMapping("/user")
    public ResponseEntity<List<WebUserResponse>> getWebUserList() throws Exception {
        List<WebUser> userList = imcUserService.getWebUserList();

        return new ResponseEntity<>(converter.toDto(userList), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<WebUser> getWebUserDetail(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
