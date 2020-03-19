package org.kimbs.netty.controller;

import lombok.RequiredArgsConstructor;
import org.kimbs.netty.client.report.ReportClient;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportClient reportClient;

}
