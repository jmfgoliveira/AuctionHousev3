package server;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Server {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/register")
    public String register(@RequestParam("email") String email,
            @RequestParam("password") String password) {
    	System.out.println("email: " + email);
    	System.out.println("password: " + password);
        return email + password;
    }
    @RequestMapping("/login")
    public Register login(@RequestParam(value="name", defaultValue="World") String name) {
        return new Register(counter.incrementAndGet(),
                            String.format(template, name));
    }
}