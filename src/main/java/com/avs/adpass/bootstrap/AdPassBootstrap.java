package com.avs.adpass.bootstrap;

import com.avs.adpass.domain.*;
import com.avs.adpass.repositories.UserRepository;
import com.avs.adpass.services.PartnerService;
import com.avs.adpass.services.UserService;
import com.avs.adpass.services.security.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AdPassBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private UserService userService;
    private PartnerService partnerService;

    public AdPassBootstrap(UserService userService, PartnerService partnerService) {
        this.userService = userService;

        this.partnerService = partnerService;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadUsersAndParnter();


    }

    private EncryptionService encryptionService;


    @Autowired
    public void setEncryptionService(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }


    public void loadUsersAndParnter() {

        Address address1 = new Address();
        address1.setAddressLine1("test");
        address1.setZipCode("11702");
        address1.setState("ny");

        Address address2 = new Address();
        address2.setAddressLine1("ptest");
        address2.setZipCode("11702");
        address2.setState("ny");




        User user1 = new User();
        user1.setUsername("al");
        user1.setPassword("theman");
        user1.setEmail("alsmaldone@yahoo.com");
        user1.setEncryptedPassword(encryptionService.encryptString(user1.getPassword()));
        user1.addAddressToUser(address1);
        address1.setUser(user1);


        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setUrl("google.com");


        UserAccount userAccount1= new UserAccount();
        userAccount1.setAccountName("Al account");
        userAccount1.setAccountBalance(new BigDecimal(10.0));
        userAccount1.addToAccountDetails(accountDetails);
        accountDetails.setUserAccount(userAccount1);

        userAccount1.setUser(user1);
        user1.setUserAccount(userAccount1);





        User user2 = new User();
        user2.setUsername("test");
        user2.setPassword("test");
        user2.setEmail("test@yahoo.com");
        user2.setEncryptedPassword(encryptionService.encryptString(user2.getPassword()));
        //user2.addAddressToUser(address1);


        Partner partner = new Partner();
        partner.setPartnerName("NYDN");
        partner.setEmail("alsmaldone@yahoo.com");
        partner.setPhoneNumber("305.333.0101");
        user1.setPartner(partner);
        user2.setPartner(partner);
        partner.addAddressToPartner(address2);
        partner.addUserToPartner(user1);
        partner.addUserToPartner(user2);
        address2.setPartner(partner);
        partnerService.savePartner(partner);



     User auser=   userService.findByUserName("al");

        userService.saveOrUpdate(auser);



    }


}
