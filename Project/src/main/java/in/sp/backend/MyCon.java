package in.sp.backend;


import com.example.dao.UserDAO;
import com.example.dao.UserDAOImpl;
import com.example.dto.UserDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MyCon {
    
    private UserDAO userDAO = new UserDAOImpl();

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register"; // JSP name
    }

    @PostMapping("/register")
    public String register(@RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           Model model) {
        UserDTO user = new UserDTO();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        boolean success = userDAO.registerUser(user);

        if (success) {
            model.addAttribute("msg", "Registration successful! Please login.");
            return "login";
        } else {
            model.addAttribute("msg", "Registration failed! Try again.");
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        UserDTO user = userDAO.loginUser(email, password);

        if (user != null) {
            model.addAttribute("name", user.getName());
            return "welcome";
        } else {
            model.addAttribute("msg", "Invalid email or password!");
            return "login";
        }
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // JSP name
    }

   
    
}
