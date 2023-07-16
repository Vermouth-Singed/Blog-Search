package com.search.blog;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JasyptTests {
    @Value("${jasypt.key}")
    private String jasyptKey;

    PooledPBEStringEncryptor encryptor;

    @BeforeEach
    void init() {
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(jasyptKey);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");

        encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(config);
    }

    @Test
    @DisplayName("jasypt 테스트")
    void jasypt_테스트() {
        String plainText = "asdf";

        String encryptText = encryptor.encrypt(plainText);
        System.out.println(encryptText);

        String decryptText = encryptor.decrypt(encryptText);
        System.out.println(decryptText);

        assertThat(plainText).isEqualTo(decryptText);
    }
}
