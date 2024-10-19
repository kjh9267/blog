package me.jun.core.guestbook.application;

import me.jun.core.guestbook.application.exception.PostCountNotFoundException;
import me.jun.core.guestbook.domain.PostCount;
import me.jun.core.guestbook.domain.repository.PostCountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static me.jun.core.guestbook.PostCountFixture.postCount;
import static me.jun.core.guestbook.PostFixture.POST_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostCountServiceTest {

    @Mock
    private PostCountRepository postCountRepository;

    private PostCountService postCountService;

    @BeforeEach
    void setUp() {
        postCountService = new PostCountService(postCountRepository);
    }

    @Test
    void createPostCountTest() {
        given(postCountRepository.save(any()))
                .willReturn(postCount());

        PostCount postCount = postCountService.createPostCount(POST_ID);

        assertThat(postCount).isEqualToComparingFieldByField(postCount());
    }

    @Test
    void updateHitsTest() {
        given(postCountRepository.findByPostId(any()))
                .willReturn(Optional.of(postCount()));

        assertThat(postCountService.updateHits(POST_ID))
                .isEqualTo(1L);
    }

    @Test
    void noPostCount_updateHitsFailTest() {
        given(postCountRepository.findByPostId(any()))
                .willThrow(PostCountNotFoundException.class);

        assertThrows(
                PostCountNotFoundException.class,
                () -> postCountService.updateHits(POST_ID)
        );
    }

    @Test
    void deletePostCountTest() {
        given(postCountRepository.findByPostId(any()))
                .willReturn(Optional.of(postCount()));

        doNothing()
                .when(postCountRepository)
                .deleteById(any());

        postCountService.deletePostCount(POST_ID);

        verify(postCountRepository)
                .deleteById(POST_ID);
    }
}