package me.jun.guestbook.guest.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.application.CommentService;
import me.jun.guestbook.guest.application.exception.GuestNotFoundException;
import me.jun.guestbook.guest.domain.Guest;
import me.jun.guestbook.guest.domain.GuestRepository;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import me.jun.guestbook.post.application.PostService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final PostService postService;

    private final CommentService commentService;

    public GuestResponse retrieveGuestBy(String email) {
        Guest guest = guestRepository.findByEmail(email)
                .orElseThrow(() -> new GuestNotFoundException());

        return GuestResponse.from(guest);
    }

    public void deleteGuest(Long guestId) {
        guestRepository.deleteById(guestId);
        postService.deletePostByWriterId(guestId);
        commentService.deleteCommentByWriterId(guestId);
    }
}
