package me.jun.guestbook.service;

import me.jun.guestbook.domain.post.TempPost;
import me.jun.guestbook.domain.post.tempPostRepository;
import me.jun.guestbook.dto.PostReadDto;
import me.jun.guestbook.dto.PostSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    tempPostRepository tempPostRepository;

    public List<PostReadDto> getPost() {
        List<TempPost> tempPosts = tempPostRepository.findAll();
        List<PostReadDto> list = new ArrayList<>();

        for(TempPost tempPost : tempPosts) {
            list.add(PostReadDto.from(tempPost));
        }
        return list;
    }

    public void savePost(PostSaveDto dto) {
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
