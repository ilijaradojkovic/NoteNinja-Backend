package com.noteninja.noteninjabackend;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileOutputStream;
import java.security.*;

@SpringBootApplication
public class NoteNinjaBackendApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(NoteNinjaBackendApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);

            kpg = KeyPairGenerator.getInstance("RSA");
            KeyPair kp = kpg.generateKeyPair();
            PrivateKey aPrivate = kp.getPrivate();
            PublicKey aPublic = kp.getPublic();
            try (FileOutputStream outPrivate = new FileOutputStream("key.priv")) {
                outPrivate.write(aPrivate.getEncoded());
            }

            try (FileOutputStream outPublic = new FileOutputStream("key.pub")) {
                outPublic.write(aPublic.getEncoded());
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
