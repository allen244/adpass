package com.avs.adpass.services;

import com.avs.adpass.domain.Partner;

import java.util.List;

public interface PartnerService {

    Partner findById(Long id);

    void deleteById(Long Id);

    List<Partner> getPartners();

    Partner savePartner(Partner partner);
}
