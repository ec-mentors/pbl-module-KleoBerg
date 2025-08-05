package io.everyonecodes.BBRoutines.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * This method adds a simple, automatic controller to the application.
     * It configures a "view controller" that forwards the root URL ("/")
     * to the specified view name, which in our case is a redirect to the main
     * routine dashboard. This provides a clean entry point to the web UI.
     *
     * @param registry The registry to which the view controller is added.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // This line says: "When a GET request comes in for the root path ('/'),
        // don't look for a static file. Instead, redirect the user's browser
        // to the '/web/routines' URL."
        registry.addRedirectViewController("/", "/web/routines");
    }
}