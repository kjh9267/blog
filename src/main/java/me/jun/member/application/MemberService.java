package me.jun.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.PostService;
import me.jun.member.application.dto.MemberResponse;
import me.jun.member.application.exception.MemberNotFoundException;
import me.jun.member.domain.Member;
import me.jun.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PostService postService;

    private final CommentService commentService;

    @Transactional(readOnly = true)
    public MemberResponse retrieveMemberBy(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException(email));

        return MemberResponse.from(member);
    }

    public void deleteMember(String email) {
        memberRepository.deleteByEmail(email);
        postService.deletePostByWriterEmail(email);
        commentService.deleteCommentByWriterEmail(email);
    }
}
