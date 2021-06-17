package me.jun.guestbook.post.infra;

import lombok.RequiredArgsConstructor;
import me.jun.guestbook.guest.application.GuestAuthService;
import me.jun.guestbook.guest.application.GuestService;
import me.jun.guestbook.guest.presentation.dto.GuestResponse;
import me.jun.guestbook.post.application.WriterService;
import me.jun.guestbook.post.presentation.dto.WriterInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WriterServiceImpl implements WriterService {

    private final GuestService guestService;

    @Override
    public WriterInfo findWriterByEmail(String email) {
        GuestResponse guestResponse = guestService.retrieveGuestBy(email);
        return WriterInfo.from(guestResponse);
    }
}
