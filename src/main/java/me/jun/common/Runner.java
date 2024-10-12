//package me.jun.common;
//
//import lombok.RequiredArgsConstructor;
//import me.jun.core.member.domain.Member;
//import me.jun.core.member.domain.Password;
//import me.jun.core.member.domain.repository.MemberRepository;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.context.annotation.Profile;
//import org.springframework.stereotype.Component;
//
//import static me.jun.core.member.domain.Role.ADMIN;
//
////@Component
//@RequiredArgsConstructor
//@Profile("test")
//public class Runner implements ApplicationRunner {
//
//    private final MemberRepository memberRepository;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        Member admin = Member.builder()
//                .name("junho")
//                .email("admin@admin.com")
//                .role(ADMIN)
//                .password(new Password("123"))
//                .build();
//
//        memberRepository.save(admin);
//    }
//}
