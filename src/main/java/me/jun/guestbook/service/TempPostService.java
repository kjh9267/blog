package me.jun.guestbook.service;

import me.jun.guestbook.dto.PostReadDto;
import me.jun.guestbook.dto.PostSaveDto;

import java.util.List;

public interface TempPostService {

    List<PostReadDto> getPost();

    void savePost(PostSaveDto postSaveDto);

    void deletePost(Long id, String password);
}
