package com.humuson.imc.admin.web.user.controller;

import com.humuson.imc.admin.web.user.dto.WebUserDetailResponse;
import com.humuson.imc.admin.web.user.dto.WebUserResponse;
import com.humuson.imc.admin.web.user.service.ImcUserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/user")
    public ResponseEntity<List<WebUserResponse>> getWebUserList() throws Exception {
        List<WebUserResponse> userList = imcUserService.getWebUserList();

        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<WebUserDetailResponse> getWebUserDetail(@PathVariable Long id) throws Exception {
        WebUserDetailResponse webUserDetail = imcUserService.getWebUserDetail(id);

        return new ResponseEntity<>(webUserDetail, HttpStatus.OK);
    }
}
