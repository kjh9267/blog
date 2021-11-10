package me.jun.guestbook.infra;

import lombok.RequiredArgsConstructor;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import me.jun.guestbook.application.PostWriterService;
import me.jun.guestbook.application.dto.PostWriterInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriterServiceImpl implements PostWriterService {

    private final MemberService memberService;

    @Override
    public PostWriterInfo retrievePostWriterBy(String email) {
        MemberResponse memberResponse = memberService.retrieveMemberBy(email);
        return PostWriterInfo.from(memberResponse);
    }
}
