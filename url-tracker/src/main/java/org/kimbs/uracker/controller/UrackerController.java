package org.kimbs.uracker.controller;

import com.sun.istack.internal.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kimbs.uracker.model.UrackerTarget;
import org.kimbs.uracker.service.UrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UrackerController {

    private final UrackerService urackerService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getUrl(@PathVariable String id) throws URISyntaxException {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> postUrl(@RequestBody @NotNull UrackerTarget target) {
        return null;
    }
}
