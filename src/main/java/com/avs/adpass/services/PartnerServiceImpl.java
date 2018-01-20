package com.avs.adpass.services;

import com.avs.adpass.commands.RegistrationForm;
import com.avs.adpass.converters.RegisterUserToUser;
import com.avs.adpass.domain.Partner;
import com.avs.adpass.domain.User;
import com.avs.adpass.repositories.PartnerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PartnerServiceImpl implements PartnerService {

    private PartnerRepository partnerRepository;
    private RegisterUserToUser registerUserToUser;


    public PartnerServiceImpl(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    @Autowired
    public void setRegisterUserToUser(RegisterUserToUser registerUserToUser) {
        this.registerUserToUser = registerUserToUser;
    }


    @Override
    public List<Partner> getPartners() {
        List<Partner> partners = new ArrayList<>();
        partnerRepository.findAll().forEach(partners::add); //fun with Java 8
        return partners;
    }


    @Override
    public Partner savePartner(Partner partner) {
        Partner p = partnerRepository.save(partner);


        return p;
    }

    @Override
    public User registerUser(Partner partner, RegistrationForm registrationForm) {
        User user = registerUserToUser.convert(registrationForm);
        partner.addUserToPartner(user);
        Partner p = partnerRepository.save(partner);
        return user;
    }

    @Override
    public Partner findById(Long id) {
        Optional<Partner> partner = partnerRepository.findById(id);
        if (!partner.isPresent()) {
            throw new RuntimeException("Role" +
                    " Not Found!");

        }


        return partner.get();
    }


    @Override
    public Partner findByParterName(String id) {
        Partner partner = partnerRepository.findByPartnerName(id);
        if (partner == null) {
            throw new RuntimeException("Role" +
                    " Not Found!");

        }


        return partner;
    }

    @Override
    public void deleteById(Long id) {
        partnerRepository.deleteById(id);
    }
}
