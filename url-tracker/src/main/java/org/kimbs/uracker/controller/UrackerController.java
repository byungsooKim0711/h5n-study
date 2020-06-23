package org.kimbs.uracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrackerController {

    @GetMapping("/")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("test");
    }
}
