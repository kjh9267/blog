package me.jun.guestbook.service;

import me.jun.guestbook.domain.post.Post;
import me.jun.guestbook.domain.post.PostRepository;
import me.jun.guestbook.dto.PostReadDto;
import me.jun.guestbook.dto.PostSaveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    public List<PostReadDto> getList() {
        List<Post> posts = postRepository.findAll();
        List<PostReadDto> list = new ArrayList<>();

        for(Post post: posts) {
            list.add(new PostReadDto(post));
        }
        return list;
    }

    public void savePost(PostSaveDto dto) {
        Post entity = dto.toEntity();
        postRepository.save(entity);
    }

    public void deletePost(Long requestId, String requestPassword) {
        Optional<Post> entity = postRepository.findById(requestId);
        String password = entity.get().getPassword();

        if(requestPassword.equals(password)) {
            postRepository.delete(entity.get());
        }
    }
}
