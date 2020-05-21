package me.jun.guestbook.service;

import me.jun.guestbook.domain.post.Post;
import me.jun.guestbook.domain.post.PostRepository;
import me.jun.guestbook.dto.PostReadDto;
import me.jun.guestbook.dto.PostSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    public List<PostReadDto> getPost() {
        List<Post> posts = postRepository.findAll();
        List<PostReadDto> list = new ArrayList<>();

        for(Post post: posts) {
            list.add(PostReadDto.from(post));
        }
        return list;
    }

    public void savePost(PostSaveDto dto) {
        Post entity = dto.toEntity();
        postRepository.save(entity);
    }

    public void deletePost(Long requestId, String requestPassword) {
        Post entity = from(requestId);
        String password = entity.getPassword();

        if(isCorrectPassword(requestPassword, password)) {
            postRepository.delete(entity);
        }
    }

    private Post from(Long requestId) {
        return postRepository.findById(requestId).get();
    }

    private boolean isCorrectPassword(String requestPassword, String password) {
        return requestPassword.equals(password);
    }
}
