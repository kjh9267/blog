package me.jun.guestbook.comment.infra;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.comment.application.CommentWriterService;
import me.jun.guestbook.comment.application.dto.CommentWriterInfo;
import me.jun.guestbook.guest.application.GuestService;
import me.jun.guestbook.guest.application.dto.GuestResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentWriterServiceImpl implements CommentWriterService {

    private final GuestService guestService;

    @Override
    public CommentWriterInfo retrieveCommentWriterBy(String email) {
        GuestResponse guestResponse = guestService.retrieveGuestBy(email);
        return CommentWriterInfo.from(guestResponse);
    }
}
