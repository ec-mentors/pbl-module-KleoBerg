package io.everyonecodes.pbl_module_KleoBerg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WebController {

    @GetMapping("/{path}")
    String site(@PathVariable String path) {
        return path;
    }
}
