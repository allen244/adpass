package com.avs.adpass.controllers;

import com.avs.adpass.commands.RegistrationForm;
import com.avs.adpass.converters.UserToUserDetails;
import com.avs.adpass.domain.AccountDetails;
import com.avs.adpass.domain.User;
import com.avs.adpass.services.UserService;
import com.avs.adpass.services.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@Controller
public class ArticleController {


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/article-reg")
    public String article(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {

            Object principal = auth.getPrincipal();

            if (principal instanceof UserDetailsImpl) {
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();

                User user = userService.findByUserName(userDetails.getUsername());
                model.addAttribute("avsUser", user);
            } else {
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getDetails();
                User user = userService.findByUserName(userDetails.getUsername());
                model.addAttribute("avsUser", user);
            }

            model.addAttribute("paid", false);


        } else {
            model.addAttribute("registerUser", new RegistrationForm());
        }

        return "article-reg";
    }

    @GetMapping("/article-regv2")
    public String articlev2(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {

            Object principal = auth.getPrincipal();

            if (principal instanceof UserDetailsImpl) {
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
                User user = userService.findByUserName(userDetails.getUsername());
                model.addAttribute("avsUser", user);
            } else {
                UserDetailsImpl userDetails = (UserDetailsImpl) auth.getDetails();
                User user = userService.findByUserName(userDetails.getUsername());
                model.addAttribute("avsUser", user);
            }

            model.addAttribute("paid", false);


        } else {
            model.addAttribute("registerUser", new RegistrationForm());
        }

        return "article-regv2";
    }

    @GetMapping({"pay"})
    public String article(Model model, HttpServletRequest request, @RequestParam String article) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = null;
            String url = request.getRequestURL().toString();


            Object principal = auth.getPrincipal();

            if (principal instanceof UserDetailsImpl) {
                userDetails = (UserDetailsImpl) auth.getPrincipal();

            } else {
                userDetails = (UserDetailsImpl) auth.getDetails();

            }


            User user = userService.findByUserName(userDetails.getUsername());
            user.getUserAccount().debit(new BigDecimal(.5));

            // List<AccountDetails> accountDetails=user.getUserAccount().getAccountDetails();


//            AccountDetails accountDetails = new AccountDetails();
//            accountDetails.setUrl(url);
//            userDetails.getUserAccount().addToAccountDetails(accountDetails);

            UserDetailsImpl avsUser = userService.updateUserAccount(user);

            model.addAttribute("avsUser", avsUser);

            model.addAttribute("paid", true);
        }

        if (article != null && article.equals("true")) {
            return "article-reg";
        } else {
            return "article-regv2";
        }
    }


}
