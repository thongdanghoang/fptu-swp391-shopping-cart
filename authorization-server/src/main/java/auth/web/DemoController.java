package auth.web;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping
    public Object demo(Authentication authentication) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.info(authentication.getClass().getName());
        return authentication.getPrincipal();
    }
}

