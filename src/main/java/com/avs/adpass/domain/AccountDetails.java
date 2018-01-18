package com.avs.adpass.domain;

import javax.persistence.*;

@Entity
@Table(name = "account_details")
public class AccountDetails extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne(cascade=CascadeType.REFRESH)
    private UserAccount userAccount;
    private String url;


    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
