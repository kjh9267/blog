package me.jun.guestbook.service;

import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.PostCreateDto;
import me.jun.guestbook.dto.PostDeleteDto;
import me.jun.guestbook.dto.PostReadDto;
import me.jun.guestbook.dto.PostReadRequestId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostServiceTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    private Post post;

    private Account account;

    @Before
    public void setUp() {
        final String name = "jun";
        final String title = "test title";
        final String content = "test content";
        final String email = "user@email.com";
        final String password = "pass";

        account = Account.builder()
                .name(name)
                .email(email)
                .password(password)
                .build();

        post = Post.builder()
                .title(title)
                .content(content)
                .build();

        post.setAccount(account);
    }

    @Test
    public void readPostTest() {
        PostReadRequestId postReadRequestId = new PostReadRequestId(1L);

        final Post savedPost = postRepository.save(post);
        final PostReadDto postReadDto = PostReadDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .account(savedPost.getAccount())
                .build();

        System.out.println(postService.readPost(postReadRequestId));

        assertThat(postService.readPost(postReadRequestId))
                .isEqualToComparingFieldByField(postReadDto);
    }

    @Test
    public void createPostTest() {
        PostReadRequestId postReadRequestId = new PostReadRequestId(1L);

        final PostCreateDto postCreateDto = PostCreateDto.builder()
                .account(account)
                .content("test content")
                .title("test title")
                .build();

        postService.createPost(postCreateDto);

        assertThat(postService.readPost(postReadRequestId))
                .isEqualToIgnoringGivenFields(postCreateDto,"id");
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void deletePostTest() {
        expectedException.expect(IllegalArgumentException.class);

        PostDeleteDto postDeleteDto = new PostDeleteDto(1L);
        PostReadRequestId postReadRequestId = new PostReadRequestId(1L);

        final PostCreateDto postCreateDto = PostCreateDto.builder()
                .account(account)
                .content("test content")
                .title("test title")
                .build();

        postService.createPost(postCreateDto);
        postService.deletePost(postDeleteDto);

        assertThat(postService.readPost(postReadRequestId));
    }
}
