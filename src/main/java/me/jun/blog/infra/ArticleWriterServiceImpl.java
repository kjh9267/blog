package me.jun.blog.infra;

import lombok.RequiredArgsConstructor;
import me.jun.blog.application.ArticleWriterService;
import me.jun.blog.application.dto.ArticleWriterInfo;
import me.jun.member.application.MemberService;
import me.jun.member.application.dto.MemberResponse;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleWriterServiceImpl implements ArticleWriterService {

    private final MemberService memberService;

    @Override
    public ArticleWriterInfo retrieveArticleWriter(String email) {
        MemberResponse memberResponse = memberService.retrieveMemberBy(email);

        return ArticleWriterInfo.builder()
                .id(memberResponse.getId())
                .email(memberResponse.getEmail())
                .name(memberResponse.getName())
                .build();
    }
}
