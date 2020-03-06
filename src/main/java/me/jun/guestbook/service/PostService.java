package me.jun.guestbook.service;

import me.jun.guestbook.domain.post.Post;
import me.jun.guestbook.domain.post.PostRepository;
import me.jun.guestbook.dto.PostReadDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
