package me.jun.common.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;

@Converter
@PropertySource("classpath:application.properties")
public class PasswordConverter implements AttributeConverter<String, String> {

    private final String ALGORITHM = "RSA";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    public PasswordConverter(@Value("#{${db-public-key}}") String publicKey,
                             @Value("#{${db-private-key}}") String privateKey) {

        initKeys(publicKey, privateKey);
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedData = cipher.doFinal(attribute.getBytes());
            byte[] encodedData = Base64.getEncoder()
                    .encode(encryptedData);

            return new String(encodedData);

        } catch (BadPaddingException | InvalidKeyException |
                IllegalBlockSizeException | NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(DECRYPT_MODE, privateKey);

            byte[] decodedData = Base64.getDecoder()
                    .decode(dbData);
            byte[] decryptedData = cipher.doFinal(decodedData);

            return new String(decryptedData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private void initKeys(String publicKey, String privateKey) {
        try {
            byte[] decodedPublicKey = Base64.getDecoder()
                    .decode(publicKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(decodedPublicKey);

            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            this.publicKey = keyFactory.generatePublic(x509EncodedKeySpec);

            byte[] decodedPrivateKey = Base64.getDecoder()
                    .decode(privateKey);

            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);

            this.privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
