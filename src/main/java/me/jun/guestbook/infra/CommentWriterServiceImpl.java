package me.jun.guestbook.infra;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentWriterService;
import me.jun.guestbook.application.dto.CommentWriterInfo;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class CommentWriterServiceImpl implements CommentWriterService {

    private final MemberService memberService;

    @Override
    public CompletableFuture<CommentWriterInfo> retrieveCommentWriterBy(String email) {
        MemberResponse memberResponse = null;
        try {
            memberResponse = memberService.retrieveMemberBy(email)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(
                CommentWriterInfo.from(memberResponse)
        );
    }
}
