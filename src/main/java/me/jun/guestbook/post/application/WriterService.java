package me.jun.guestbook.post.application;

import me.jun.guestbook.post.presentation.dto.WriterInfo;

public interface WriterService {
    WriterInfo findWriterByEmail(String email);
}
