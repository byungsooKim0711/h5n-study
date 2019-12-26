package org.kimbs.demo.controller.v2;

import org.kimbs.demo.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "memberControllerV2")
@RequestMapping(value = "/api", headers = "ORG-KIMBS-VERSION=v2")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
