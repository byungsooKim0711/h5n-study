package com.humuson.demo.web;

import com.humuson.demo.domain.send.SendHist;
import com.humuson.demo.domain.send.SendProfile;
import com.humuson.demo.domain.send.dto.BizHistDto;
import com.humuson.demo.domain.send.service.SendHistService;
import com.humuson.demo.domain.send.service.SendProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SendHistController {

    private final SendHistService sendHistService;
    private final SendProfileService sendProfileService;

    @GetMapping(value = "/send/hist")
    public ModelAndView list(@ModelAttribute SendHist hist, String companyName, ModelAndView view, @PageableDefault Pageable page) {
        view.setViewName("list");
        List<BizHistDto> hists = sendHistService.findSendHistByCompanyName(companyName, page);
        List<SendProfile> profiles = sendProfileService.findAll();

        view.addObject("hists", hists);
        view.addObject("profiles", profiles);

        return view;
    }
}
