package me.jun.guestbook.service;

import me.jun.guestbook.dto.TempPostReadDto;
import me.jun.guestbook.dto.TempPostSaveDto;

import java.util.List;

public interface TempPostService {

    List<TempPostReadDto> getPost();

    void savePost(TempPostSaveDto tempPostSaveDto);

    void deletePost(Long id, String password);
}
