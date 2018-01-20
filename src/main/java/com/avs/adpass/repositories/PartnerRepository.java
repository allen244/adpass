package com.avs.adpass.repositories;

import com.avs.adpass.domain.Partner;
import com.avs.adpass.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface PartnerRepository extends CrudRepository<Partner, Long> {

    Partner findByPartnerName(String name);
}
