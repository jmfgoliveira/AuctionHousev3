package server;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Server {

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/register")
    public String register(@RequestParam("name") String name, @RequestParam("email") String email,
            @RequestParam("password") String password) {
    	System.out.println("REGISTER");
    	System.out.println("name: " + name);
    	System.out.println("email: " + email);
    	System.out.println("password: " + password);
    	new User(counter.intValue(), name, password, email);
        return name + email + password;
    }
    
    @RequestMapping("/login")
    public String login(@RequestParam("email") String email,
            @RequestParam("password") String password) {
    	System.out.println("LOGIN");
    	System.out.println("email: " + email);
    	System.out.println("password: " + password);
    	if(email.equals("ola@ola.com") && password.equals("ola"))
    		return "LOGGED IN";
        return new String("TODO");
    }
    
    @RequestMapping("/product")
    public String product(@RequestParam("email") String email,
            @RequestParam("password") String password) {
    	System.out.println("LOGIN");
    	System.out.println("email: " + email);
    	System.out.println("password: " + password);
    	if(email.equals("ola@ola.com") && password.equals("ola"))
    		return "LOGGED IN";
        return new String("TODO");
    }
}