package me.jun.member.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentService;
import me.jun.member.application.exception.MemberNotFoundException;
import me.jun.member.domain.Member;
import me.jun.member.domain.repository.MemberRepository;
import me.jun.member.application.dto.MemberResponse;
import me.jun.guestbook.application.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    private final PostService postService;

    private final CommentService commentService;

    @Transactional(readOnly = true)
    public CompletableFuture<MemberResponse> retrieveMemberBy(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        return CompletableFuture.completedFuture(
                MemberResponse.from(member)
        )
                .exceptionally(throwable -> new MemberResponse());
    }

    public CompletableFuture<Long> deleteMember(Long memberId) {
        memberRepository.deleteById(memberId);
        postService.deletePostByWriterId(memberId);
        commentService.deleteCommentByWriterId(memberId);
        return CompletableFuture.completedFuture(null);
    }
}
