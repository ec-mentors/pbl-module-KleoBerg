package io.everyonecodes.pbl_module_KleoBerg.controller;

import io.everyonecodes.pbl_module_KleoBerg.dto.ProviderDTO;
import io.everyonecodes.pbl_module_KleoBerg.service.ProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WebController {
    private final ProviderService providerService;

    public WebController(ProviderService providerService) {
        this.providerService = providerService;
    }

//    @GetMapping("/{path}")
//    String site(@PathVariable String path) {
//        return path;
//    }


    @GetMapping("")
    public ModelAndView getIndexPage() {
        List<ProviderDTO> providers = providerService.findAllProviders();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("providers", providers);
        return modelAndView;
    }
}
