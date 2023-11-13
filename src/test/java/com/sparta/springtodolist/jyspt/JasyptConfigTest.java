package com.sparta.springtodolist.jyspt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {

    @Test
    void stringEncryptor() {
        // given
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(" ");
        String test = "test";

        // when
        String encryptPassword = encryptor.encrypt(test);

        // then
        Assertions.assertEquals(encryptor.decrypt(encryptPassword), "test");
    }
}