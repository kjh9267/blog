package me.jun.guestbook.service;

import me.jun.guestbook.domain.post.TempPost;
import me.jun.guestbook.domain.post.TempPostRepository;
import me.jun.guestbook.dto.TempPostReadDto;
import me.jun.guestbook.dto.TempPostSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TempPostServiceImpl implements TempPostService {

    @Autowired
    TempPostRepository tempPostRepository;

    public List<TempPostReadDto> getPost() {
        List<TempPost> tempPosts = tempPostRepository.findAll();
        List<TempPostReadDto> list = new ArrayList<>();

        for(TempPost tempPost : tempPosts) {
            list.add(TempPostReadDto.from(tempPost));
        }
        return list;
    }

    public void savePost(TempPostSaveDto dto) {
        TempPost entity = dto.toEntity();
        tempPostRepository.save(entity);
    }

    public void deletePost(Long requestId, String requestPassword) {
        TempPost entity = from(requestId);
        String password = entity.getPassword();

        if(isCorrectPassword(requestPassword, password)) {
            tempPostRepository.delete(entity);
        }
    }

    private TempPost from(Long requestId) {
        return tempPostRepository.findById(requestId).get();
    }

    private boolean isCorrectPassword(String requestPassword, String password) {
        return requestPassword.equals(password);
    }
}
