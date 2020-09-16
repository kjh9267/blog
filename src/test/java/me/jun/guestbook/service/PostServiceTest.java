package me.jun.guestbook.service;

import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.*;
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
        PostReadRequestDto postReadRequestDto = new PostReadRequestDto(1L);

        final Post savedPost = postRepository.save(post);
        final PostInfoDto postInfoDto = PostInfoDto.builder()
                .id(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .account(savedPost.getAccount())
                .build();

        System.out.println(postService.readPost(postReadRequestDto));

        assertThat(postService.readPost(postReadRequestDto))
                .isEqualToComparingFieldByField(postInfoDto);
    }

    @Test
    public void createPostTest() {
        PostReadRequestDto postReadRequestDto = new PostReadRequestDto(1L);

        final PostCreateRequestDto postCreateRequestDto = PostCreateRequestDto.builder()
                .account(account)
                .content("test content")
                .title("test title")
                .build();

        postService.createPost(postCreateRequestDto);

        assertThat(postService.readPost(postReadRequestDto))
                .isEqualToIgnoringGivenFields(postCreateRequestDto,"id");
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void deletePostTest() {
        expectedException.expect(IllegalArgumentException.class);

        PostDeleteRequestDto postDeleteRequestDto = new PostDeleteRequestDto(1L);
        PostReadRequestDto postReadRequestDto = new PostReadRequestDto(1L);

        final PostCreateRequestDto postCreateRequestDto = PostCreateRequestDto.builder()
                .account(account)
                .content("test content")
                .title("test title")
                .build();

        postService.createPost(postCreateRequestDto);
        postService.deletePost(postDeleteRequestDto);

        assertThat(postService.readPost(postReadRequestDto));
    }

    @Test
    public void updatePostTest() {
        final PostCreateRequestDto postCreateRequestDto = PostCreateRequestDto.builder()
                .account(account)
                .content("test content")
                .title("test title")
                .build();

        postService.createPost(postCreateRequestDto);

        final PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .account(account)
                .build();

        // When
        final PostInfoDto postInfoDto = postService.updatePost(postUpdateRequestDto);

        assertThat(postService.readPost(new PostReadRequestDto(1L)))
                .isEqualToComparingFieldByField(postInfoDto);
    }

    @Test
    public void updatePostFailTest() {
        // expected
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("wrong password");

        // Given
        final PostCreateRequestDto postCreateRequestDto = PostCreateRequestDto.builder()
                .account(account)
                .content("test content")
                .title("test title")
                .build();

        postService.createPost(postCreateRequestDto);

        Account wrongAccount = Account.builder()
                .name("jun")
                .email("user@email.com")
                .password("abc")
                .build();

        final PostUpdateRequestDto postUpdateRequestDto = PostUpdateRequestDto.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .account(wrongAccount)
                .build();

        // When
        postService.updatePost(postUpdateRequestDto);
    }
}
