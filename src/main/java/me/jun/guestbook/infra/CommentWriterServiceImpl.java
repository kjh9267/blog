package me.jun.guestbook.infra;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.application.CommentWriterService;
import me.jun.guestbook.application.dto.CommentWriterInfo;
import me.jun.guest.application.GuestService;
import me.jun.guest.application.dto.GuestResponse;
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
