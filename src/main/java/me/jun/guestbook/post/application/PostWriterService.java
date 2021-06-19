package me.jun.guestbook.post.application;

import me.jun.guestbook.post.presentation.dto.PostWriterInfo;

public interface PostWriterService {
    PostWriterInfo retrievePostWriterBy(String email);
}
