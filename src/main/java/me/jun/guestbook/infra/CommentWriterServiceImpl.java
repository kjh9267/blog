package me.jun.guestbook.infra;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentWriterService;
import me.jun.guestbook.application.dto.CommentWriterInfo;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentWriterServiceImpl implements CommentWriterService {

    private final MemberService memberService;

    @Override
    public CommentWriterInfo retrieveCommentWriterBy(String email) {
        MemberResponse memberResponse = memberService.retrieveMemberBy(email);
        return CommentWriterInfo.from(memberResponse);
    }
}
