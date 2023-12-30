package thongdanghoang.resourceserver;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/demo")
    public Authentication demo(Authentication a) {
//        BearerTokenAuthentication bta = (BearerTokenAuthentication) a;
        // bta.getTokenAttributes().get("userId").toString()
        // a.getAuthorities()
        return a;
    }
}
