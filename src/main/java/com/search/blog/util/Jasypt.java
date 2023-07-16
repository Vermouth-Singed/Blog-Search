package com.search.blog.util;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Jasypt {
    @Autowired
    StringEncryptor jasyptStringEncryptor;

    public String encrypt(String text) {
        if (text == null) {
            return "";
        }

        return jasyptStringEncryptor.encrypt(text);
    }

    public String decrypt(String text) throws Exception {
        return jasyptStringEncryptor.decrypt(text);
    }
}
