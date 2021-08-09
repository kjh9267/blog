package me.jun.guestbook.guest.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static me.jun.guestbook.guest.GuestFixture.EMAIL;
import static me.jun.guestbook.guest.GuestFixture.guest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GuestRepositoryTest {

    @Autowired
    private GuestRepository guestRepository;

    @Test
    void findByEmailTest() {
        Guest guest = guest();
        guestRepository.save(guest);

        Guest foundGuest = guestRepository.findByEmail(EMAIL).get();

        assertThat(foundGuest).isEqualToComparingFieldByField(guest);
    }
}