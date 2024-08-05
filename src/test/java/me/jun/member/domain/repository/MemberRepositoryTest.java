package me.jun.member.domain.repository;

import me.jun.member.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static me.jun.member.MemberFixture.EMAIL;
import static me.jun.member.MemberFixture.member;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByEmailTest() {
        Member member = member();
        memberRepository.save(member);

        Member foundMember = memberRepository.findByEmail(EMAIL).get();

        assertThat(foundMember).isEqualToComparingFieldByField(member);
    }

    @Test
    void deleteByEmailTest() {
        Member member = member();
        memberRepository.save(member);

        memberRepository.deleteByEmail(EMAIL);

        assertThat(memberRepository.findByEmail(EMAIL))
                .isEqualTo(Optional.empty());
    }
}