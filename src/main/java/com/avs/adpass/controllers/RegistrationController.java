package com.avs.adpass.controllers;

import com.avs.adpass.commands.RegistrationForm;
import com.avs.adpass.domain.Partner;
import com.avs.adpass.domain.User;
import com.avs.adpass.services.PartnerService;
import com.avs.adpass.services.UserService;
import com.avs.adpass.services.security.SecurityUserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Controller
public class RegistrationController {

    private PartnerService partnerService;
    private UserService userService;
    private SecurityUserDetailsServiceImpl securityUserDetailsService;

    private AuthenticationProvider authenticationProvider;


    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }


    @Autowired
    public void setSecurityUserDetailsService(SecurityUserDetailsServiceImpl securityUserDetailsService) {
        this.securityUserDetailsService = securityUserDetailsService;
    }

    @Autowired
    public void setPartnerService(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("register")
    public String saveOrUpdate(@Valid @ModelAttribute("registerUser") RegistrationForm registrationForm, BindingResult bindingResult, HttpServletRequest request) {

        Partner partner = partnerService.findByParterName("NYDN");


        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "article-reg";
        }

        User user = userService.registerUser(partner, registrationForm);
        partner.addUserToPartner(user);
      //  partnerService.savePartner(partner);


        if (user != null) {

            UserDetails userDetails = securityUserDetailsService.loadUserByUsername(user.getUsername());

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());

            token.setDetails(userDetails);
            SecurityContextHolder.getContext().setAuthentication(token);


        }

        return "redirect:/";
    }


}
