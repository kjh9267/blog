package me.jun.guestbook.infra;

import lombok.RequiredArgsConstructor;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import me.jun.guestbook.application.PostWriterService;
import me.jun.guestbook.application.dto.PostWriterInfo;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PostWriterServiceImpl implements PostWriterService {

    private final MemberService memberService;

    @Override
    public CompletableFuture<PostWriterInfo> retrievePostWriterBy(String email) {
        MemberResponse memberResponse = null;
        try {
            memberResponse = memberService.retrieveMemberBy(email)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(
                PostWriterInfo.from(memberResponse)
        );
    }
}
