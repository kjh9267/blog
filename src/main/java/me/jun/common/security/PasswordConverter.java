package me.jun.common.security;

import sun.security.rsa.RSAKeyPairGenerator;

import javax.crypto.Cipher;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

@Converter
public class PasswordConverter implements AttributeConverter<String, String> {

    private final String ALGORITHM = "RSA";

    private final PublicKey publicKey;

    private final PrivateKey privateKey;

    private final Encoder base64Encoder = Base64.getEncoder();

    private final Decoder base64Decoder = Base64.getDecoder();

    public PasswordConverter() {
        RSAKeyPairGenerator rsaKeyPairGenerator = new RSAKeyPairGenerator();
        double keySize = Math.pow(2, 12);
        rsaKeyPairGenerator.initialize((int) keySize, new SecureRandom());

        KeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();

        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedData = cipher.doFinal(attribute.getBytes());
            byte[] encodedData = base64Encoder.encode(encryptedData);

            return new String(encodedData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decodedData = base64Decoder.decode(dbData);
            byte[] decryptedData = cipher.doFinal(decodedData);

            return new String(decryptedData);
        } catch (Exception e) {
            // wrong key, padding,
            throw new RuntimeException(e);
        }
    }
}
