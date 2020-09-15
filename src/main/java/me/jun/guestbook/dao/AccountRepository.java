package me.jun.guestbook.dao;

import me.jun.guestbook.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
