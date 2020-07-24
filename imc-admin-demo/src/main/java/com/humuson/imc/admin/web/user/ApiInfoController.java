package com.humuson.imc.admin.web.user;

import com.humuson.imc.admin.web.domain.user.ApiInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ApiInfoController {

    private final ApiInfoService apiInfoService;


    @GetMapping("/user/api")
    public ResponseEntity<List<ApiInfo>> getApiInfoList() throws Exception {
        List<ApiInfo> apiInfoList = apiInfoService.getApiInfoList();

        return new ResponseEntity<>(apiInfoList, HttpStatus.OK);
    }
}
