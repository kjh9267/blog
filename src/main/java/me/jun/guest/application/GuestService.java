package me.jun.guest.application;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentService;
import me.jun.guest.application.exception.GuestNotFoundException;
import me.jun.guest.domain.Guest;
import me.jun.guest.domain.repository.GuestRepository;
import me.jun.guest.application.dto.GuestResponse;
import me.jun.guestbook.application.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    private final GuestRepository guestRepository;

    private final PostService postService;

    private final CommentService commentService;

    @Transactional(readOnly = true)
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
