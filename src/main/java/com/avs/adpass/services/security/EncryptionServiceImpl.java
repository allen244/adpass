package com.avs.adpass.services.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EncryptionServiceImpl implements EncryptionService {


    private PasswordEncoder strongEncryptor;


    @Autowired
    public void setStrongEncryptor(PasswordEncoder strongEncryptor) {
        this.strongEncryptor = strongEncryptor;
    }

    public String encryptString(String input) {
        return strongEncryptor.encode(input);
    }

    public boolean checkPassword(String plainPassword, String encryptedPassword) {


        return BCrypt.checkpw(plainPassword, encryptedPassword);
    }


}
