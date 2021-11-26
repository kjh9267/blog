package me.jun.blog.infra;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.ArticleWriterService;
import me.jun.blog.application.dto.ArticleWriterInfo;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class ArticleWriterServiceImpl implements ArticleWriterService {

    private final MemberService memberService;

    @Override
    public CompletableFuture<ArticleWriterInfo> retrieveArticleWriter(String email) {
        MemberResponse memberResponse = null;
        try {
            memberResponse = memberService.retrieveMemberBy(email)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(
                ArticleWriterInfo.builder()
                        .id(memberResponse.getId())
                        .email(memberResponse.getEmail())
                        .name(memberResponse.getName())
                        .build()
        );
    }
}
