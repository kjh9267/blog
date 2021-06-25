package me.jun.guestbook.guest.application;

import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import me.jun.guestbook.post.application.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GuestServiceTest {

    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    private Guest guest;

    private GuestResponse guestResponse;

    @BeforeEach
    void setUp() {
        guestService = new GuestService(guestRepository, postService, commentService);

        guest = Guest.builder()
                .id(1L)
                .name("test user")
                .email("testuser@email.com")
                .build();

        guestResponse = GuestResponse.from(guest);
    }

    @Test
    void retrieveGuestByEmailTest() {
        given(guestRepository.findByEmail(any()))
                .willReturn(Optional.of(guest));

        assertThat(guestService.retrieveGuestBy("testuser@email.com"))
                .isEqualToComparingFieldByField(guestResponse);
    }

    @Test
    void deleteGuestTest() {
        doNothing().when(guestRepository)
                .deleteById(any());
        doNothing().when(postService)
                .deletePostByWriterId(any());
        doNothing().when(commentService)
                .deleteCommentByWriterId(any());

        guestService.deleteGuest(1L);

        verify(guestRepository).deleteById(1L);
        verify(postService).deletePostByWriterId(1L);
        verify(commentService).deleteCommentByWriterId(1L);
    }
}