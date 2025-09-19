package in.sp.backend;


import com.example.dao.UserDAO;

import com.example.dao.UserDAOImpl;
import com.example.dto.UserDTO;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.dao.RatingDAO;
import com.example.dao.RatingDAOImpl;
import com.example.dto.RatingDTO;


@Controller
public class MyCon {
    
    private UserDAO userDAO = new UserDAOImpl();
    private RatingDAO ratingDAO = new RatingDAOImpl();


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

   /* @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        UserDTO user = userDAO.loginUser(email, password);

        if (user != null) {
            model.addAttribute("name", user.getName());
            return "welcome";
        } else{
            model.addAttribute("msg", "Invalid email or password! If not Registerd, Kindly Register");
            return "login";
        
    }}*/
    
    @PostMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model) {
        UserDTO user = userDAO.loginUser(email, password);

        if (user != null) {
            model.addAttribute("name", user.getName());

           
            RatingDTO rating = ratingDAO.getRatingByUser(user.getName());
            if (rating != null) {
                model.addAttribute("userRating", rating.getRating());
            }


            return "welcome";
        } else {
            model.addAttribute("msg", "Invalid email or password! If not registered, kindly register.");
            return "login";
        }
    }

    @PostMapping("/rate")
    public String rate(@RequestParam("name") String name,
                       @RequestParam("rating") int rating,
                       Model model) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setUserName(name);
        ratingDTO.setRating(rating);

        boolean success = ratingDAO.saveRating(ratingDTO);

        if (success) {
            model.addAttribute("msg", "Thanks for rating!");
            model.addAttribute("userRating", rating);
        } else {
            model.addAttribute("msg", "You have already rated. Cannot rate again!");
            model.addAttribute("userRating", ratingDAO.getRatingByUser(name).getRating());
        }

        model.addAttribute("name", name);
        return "welcome";
    }

   


    


    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // JSP name
    }

   
    
}
