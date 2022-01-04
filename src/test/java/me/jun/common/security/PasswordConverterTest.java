package me.jun.common.security;

import org.junit.jupiter.api.Test;

import static me.jun.common.security.KeyFixture.DB_PRIVATE_KEY;
import static me.jun.common.security.KeyFixture.DB_PUBLIC_KEY;
import static me.jun.member.MemberFixture.PASSWORD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
}