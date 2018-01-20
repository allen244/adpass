package com.avs.adpass.services;

import com.avs.adpass.commands.RegistrationForm;
import com.avs.adpass.domain.Partner;
import com.avs.adpass.domain.User;

import java.util.List;

public interface PartnerService {

    Partner findById(Long id);

    Partner findByParterName(String partnername);

    void deleteById(Long Id);

    List<Partner> getPartners();

    Partner savePartner(Partner partner);

    User registerUser(Partner partner, RegistrationForm registrationForm);
}
