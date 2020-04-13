package com.humuson.demo.controller;

import com.humuson.demo.domain.SendHist;
import com.humuson.demo.service.SendHistService;
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


    @GetMapping(value = "/send/hist")
    public ModelAndView list(@ModelAttribute SendHist hist, ModelAndView view, @PageableDefault(page = 0, size = 10) Pageable page) {
        view.setViewName("index");
        List<SendHist> hists = sendHistService.findAll(page).getContent();

        view.addObject("hists", hists);
        return view;
    }
}
