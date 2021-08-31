package me.jun.guestbook.post.infra;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.GuestService;
import me.jun.guestbook.guest.application.dto.GuestResponse;
import me.jun.guestbook.post.application.PostWriterService;
import me.jun.guestbook.post.application.dto.PostWriterInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostWriterServiceImpl implements PostWriterService {

    private final GuestService guestService;

    @Override
    public PostWriterInfo retrievePostWriterBy(String email) {
        GuestResponse guestResponse = guestService.retrieveGuestBy(email);
        return PostWriterInfo.from(guestResponse);
    }
}
