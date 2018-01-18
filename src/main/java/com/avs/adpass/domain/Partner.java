package com.avs.adpass.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "partner")
public class Partner extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String partnerName;
    private String email;
    private String phoneNumber;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner")
    private List<User> users = new ArrayList<>();


    @Override
    public Long getId() {
        return id;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUserToPartner(User user) {
        user.setPartner(this);
        users.add(user);
    }

    public void removeUserToPartner(User user) {
        user.setPartner(null);
        users.remove(user);
    }


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partner")
    private List<Address> addresses = new ArrayList<>();

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddressToPartner(Address address) {
        address.setPartner(this);
        addresses.add(address);
    }

    public void removeAddressToPartner(Address address) {
        address.setUser(null);
        addresses.remove(address);
    }


}
