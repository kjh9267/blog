package me.jun.member.application;

import me.jun.guestbook.application.CommentService;
import me.jun.guestbook.application.PostService;
import me.jun.member.domain.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static me.jun.guestbook.PostFixture.WRITER_EMAIL;
import static me.jun.member.MemberFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository, postService, commentService);
    }

    @Test
    void retrieveMemberByEmailTest() throws ExecutionException, InterruptedException {
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(member()));

        assertThat(memberService.retrieveMemberBy("testuser@email.com").get())
                .isEqualToComparingFieldByField(memberResponse());
    }

    @Test
    void deleteMemberTest() {
        doNothing().when(memberRepository)
                .deleteByEmail(any());

        memberService.deleteMember(EMAIL);

        verify(memberRepository).deleteByEmail(EMAIL);
        verify(postService).deletePostByWriterEmail(WRITER_EMAIL);
        verify(commentService).deleteCommentByWriterEmail(EMAIL);
    }
}