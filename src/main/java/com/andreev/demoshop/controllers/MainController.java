package com.andreev.demoshop.controllers;

import com.andreev.demoshop.aspect.Logger;
import com.andreev.demoshop.service.SessionObjectHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class MainController {

    private final SessionObjectHolder sessionObjectHolder;

    public MainController(SessionObjectHolder sessionObjectHolder) {
        this.sessionObjectHolder = sessionObjectHolder;
    }

    @RequestMapping({"", "/"})
    public String index(Model model, HttpSession httpSession) {
//        model.addAttribute("amountClicks", sessionObjectHolder.getAmountClicks());
//        if (httpSession.getAttribute("myID") == null) {
//            String uuid = UUID.randomUUID().toString();
//            httpSession.setAttribute("myID", uuid);
//            System.out.println("Generated UUID -> " + uuid);
//        }
//        model.addAttribute("uuid", httpSession.getAttribute("myID"));
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error") // чтобы пользователь попал на 404-page
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @RequestMapping("/loging")
    public String logging(Model model) {
        model.addAttribute("loging", Logger.loggerList);
        return "loging";
    }
}