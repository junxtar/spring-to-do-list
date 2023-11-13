package com.sparta.springtodolist.jyspt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {

    @Test
    void stringEncryptor() {
        // given
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(" ");
        String test = "jdbc:mysql://localhost:3306/card";

        // when, then
        String actual = encryptor.encrypt(test);
//        System.out.println(encryptor.encrypt("jdbc:mysql://localhost:3306/card"));
//        System.out.println(encryptor.encrypt("root"));
//        System.out.println(encryptor.encrypt("ekqls832@@"));
//        Assertions.assertEquals(test, encryptor.decrypt(actual));
        System.out.println(encryptor.decrypt("CKc/k4oHyy+uNSRS/LsuU6l/OysBk7mV"));
//        Assertions.assertEquals("ekqls832@@", encryptor.decrypt("uf6E2yWW/snggbzESvbdqnCxJe7sKURo"));
    }
}