package inflearn.kimyounghan.thymeleafbasic.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("text-basic")
    public String textBasic(Model model) {
        model.addAttribute("data", "Hello, Spring!");
        return "basic/text-basic";
    }

    @GetMapping("text-unescaped")
    public String textUnescaped(Model model) {
        model.addAttribute("data", "<b>Hello, Spring!</b>");
        return "basic/text-unescaped";
    }

    @GetMapping("variable")
    public String variable(Model model) {
        User userA = new User("userA", 23);
        User userB = new User("userB", 29);

        List<User> userList = new ArrayList<>();
        userList.add(userA);
        userList.add(userB);

        Map<String, User> userMap = new HashMap<>();
        userMap.put(userA.getName(), userA);
        userMap.put(userB.getName(), userB);

        model.addAttribute("userA", userA);
        model.addAttribute("userList", userList);
        model.addAttribute("userMap", userMap);

        return "basic/variable";
    }

    @GetMapping("objects")
    public String basicObjects(HttpSession session) {
        session.setAttribute("sessionData", "Hello, Spring MVC!");
        return "basic/objects";
    }

    @GetMapping("link")
    public String link(Model model) {
        model.addAttribute("param1", "data1");
        model.addAttribute("param2", "data2");
        return "basic/link";
    }

    @GetMapping("literal")
    public String literal(Model model) {
        model.addAttribute("data", "Spring MVC 2!");
        return "basic/literal";
    }

    @GetMapping("operation")
    public String operation(Model model) {
        model.addAttribute("nullData", null);
        model.addAttribute("data", "Spring MVC 2!");
        return "basic/operation";
    }

    @GetMapping("attribute")
    public String attribute() {
        return "basic/attribute";
    }

    @GetMapping("loop")
    public String loop(Model model) {
        addUsers(model);
        return "basic/loop";
    }

    @GetMapping("condition")
    public String condition(Model model) {
        addUsers(model);
        return "basic/condition";
    }

    @GetMapping("comment")
    public String comment(Model model) {
        model.addAttribute("data", "Spring MVC2!");
        return "basic/comment";
    }

    @GetMapping("block")
    public String block(Model model) {
        addUsers(model);
        return "basic/block";
    }

    @GetMapping("javascript")
    public String javascript(Model model) {
        addUsers(model);
        model.addAttribute("user", new User("UserD", 40));
        return "basic/javascript";
    }

    private void addUsers(Model model) {
        List<User> users = new ArrayList<>();
        users.add(new User("UserA", 10));
        users.add(new User("UserB", 20));
        users.add(new User("UserC", 30));
        model.addAttribute("users", users);
    }

    //    @Component
    @Component("helloBean")
    static class HelloBean {
        public String hello(String data) {
            return "Hello, " + data;
        }
    }

    @Data
    @AllArgsConstructor
    static class User {

        private String name;
        private Integer age;
    }
}
