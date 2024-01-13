package com.ride.amt.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("name")
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String goToLoginPage(ModelMap model) {
        String username = getLoginUsername();
        logger.debug("go to welcome page\nusername: {}", username);
        model.addAttribute("name", username);
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(ModelMap model) {
        String username = getLoginUsername();
        logger.debug("go to welcome page\nusername: {}", username);
        model.addAttribute("name", username);
        return "welcome";
    }

    private String getLoginUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
