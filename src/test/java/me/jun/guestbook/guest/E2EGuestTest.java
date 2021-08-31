package me.jun.guestbook.guest;

import me.jun.guestbook.support.E2ETest;
import org.junit.jupiter.api.Test;

public class E2EGuestTest extends E2ETest {

    @Test
    void guestTest() {
        register();
        token = login();
    }
}
