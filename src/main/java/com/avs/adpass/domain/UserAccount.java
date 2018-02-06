package com.avs.adpass.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_account")
public class UserAccount extends AbstractDomain {


    private String accountName;
    private BigDecimal accountBalance;

    @OneToOne(cascade = {CascadeType.ALL})
    private User user;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userAccount", orphanRemoval = true)
    private List<AccountDetails> accountDetails = new ArrayList<>();

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<AccountDetails> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(List<AccountDetails> accountDetails) {
        this.accountDetails = accountDetails;
    }

    public void addToAccountDetails(AccountDetails details) {
        details.setUserAccount(this);
        accountDetails.add(details);
    }

    public void removeAccountDetails(AccountDetails details) {
        details.setUserAccount(null);
        accountDetails.remove(accountDetails);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void deposit(BigDecimal amount) {
        BigDecimal sum = this.getAccountBalance().add(amount);
        this.setAccountBalance(sum);

    }

    public void debit(BigDecimal amount) {
        BigDecimal remove = this.getAccountBalance().subtract(amount);
        DecimalFormat df = new DecimalFormat("##.00");
        String value= df.format(remove);
        this.setAccountBalance(new BigDecimal(value));

    }

}
