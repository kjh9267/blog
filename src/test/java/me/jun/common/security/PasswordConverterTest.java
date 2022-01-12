package me.jun.common.security;

import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;

import static me.jun.common.security.KeyFixture.*;
import static me.jun.member.MemberFixture.PASSWORD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordConverterTest {

    @Test
    void passwordConverterTest() {
        PasswordConverter passwordConverter = new PasswordConverter(
                DB_PUBLIC_KEY,
                DB_PRIVATE_KEY
        );

        String encryptedData = passwordConverter.convertToDatabaseColumn(PASSWORD);

        String decryptedData = passwordConverter.convertToEntityAttribute(encryptedData);

        assertThat(decryptedData)
                .isEqualTo(PASSWORD);
    }

    @Test
    void InvalidKeyExceptionTest() {
        assertThrows(
                RuntimeException.class,
                () -> new PasswordConverter(
                        DB_PRIVATE_KEY,
                        DB_PUBLIC_KEY
                )
        );
    }
}