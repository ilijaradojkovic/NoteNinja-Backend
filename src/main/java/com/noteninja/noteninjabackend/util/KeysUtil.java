package com.noteninja.noteninjabackend.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class KeysUtil {
    private final ResourceLoader resourceLoader;

    public String decode(String text) throws Exception {

        PrivateKey privateKey = loadPrivateKey(getPathToResourceFile("keypriv"));


        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(bytes);

    }

    public String getPathToResourceFile(String fileName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + "/keys/" + fileName);
        return resource.getFile().getAbsolutePath();
    }

    private PrivateKey loadPrivateKey(String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        // reading from resource folder
        byte[] privateKeyBytes = Files.readAllBytes(Paths.get(filePath));

        KeyFactory privateKeyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = privateKeyFactory.generatePrivate(privateKeySpec);
        return privateKey;
    }

    private PublicKey loadPublicKey(String filePath) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        // reading from resource folder
        byte[] publicKeyBytes = Files.readAllBytes(Paths.get(filePath));

        KeyFactory publicKeyFactory = KeyFactory.getInstance("RSA");
        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey = publicKeyFactory.generatePublic(publicKeySpec);
        return publicKey;
    }

    public String encode(String toEncode) throws Exception {

        PublicKey publicKey = loadPublicKey(getPathToResourceFile("keypub"));

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bytes = cipher.doFinal(toEncode.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(bytes));
    }
}
