package me.jun.guestbook.application;

import me.jun.guestbook.application.dto.PostWriterInfo;

public interface PostWriterService {
    PostWriterInfo retrievePostWriterBy(String email);
}
