package one.adf.testredirectbeanleak;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@SpringBootApplication
@Controller
@EnableAspectJAutoProxy
public class TestRedirectBeanLeakApplication {


    private static final String LONG_STRING = "a".repeat(1_000);
    private static final AtomicLong n = new AtomicLong();

    public static void main(String[] args) {
        SpringApplication.run(TestRedirectBeanLeakApplication.class, args);
    }

    @GetMapping("/")
    public String index(RedirectAttributes attributes) {
        System.out.println(n);
        Map<String ,String>  map = new HashMap<>();
        map.put(n+"",n+"");
        attributes.addFlashAttribute("q",map);
        return "redirect:/abc";
    }

    @GetMapping("/abc")
    public String abc(@ModelAttribute("q") Map<String,String> q) {
        System.out.println(q.toString());
        return "index.html";
    }

}
