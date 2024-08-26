package me.jun.core.member.domain.repository;

import me.jun.core.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    void deleteByEmail(String email);
}
