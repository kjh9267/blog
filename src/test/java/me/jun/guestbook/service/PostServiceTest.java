package me.jun.guestbook.service;

import me.jun.guestbook.dao.AccountRepository;
import me.jun.guestbook.dao.PostRepository;
import me.jun.guestbook.domain.Account;
import me.jun.guestbook.domain.Post;
import me.jun.guestbook.dto.*;
import me.jun.guestbook.exception.PostNotFoundException;
import me.jun.guestbook.exception.WrongPasswordException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private AccountRepository accountRepository;

    private Post post;

    private Account account;

    @Before
    public void setUp() {
        String name = "jun";
        String title = "test title";
        String content = "test content";
        String email = "testuser@email.com";
        String password = "pass";

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
        accountRepository.save(account);

        Post savedPost = postRepository.save(post);
        PostResponseDto postResponseDto = PostResponseDto.of(savedPost, account);

        assertThat(postService.readPost(postReadRequestDto))
                .isEqualToComparingFieldByField(postResponseDto);
    }

    @Test
    public void createPostTest() {
        PostReadRequestDto postReadRequestDto = new PostReadRequestDto(1L);

        PostCreateRequestDto postCreateRequestDto = createPostCreateRequestDto();
        postService.createPost(postCreateRequestDto);

        assertThat(postService.readPost(postReadRequestDto))
                .isEqualToIgnoringGivenFields(postCreateRequestDto,"id", "accountEmail", "accountName");
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void deletePostTest() {
        expectedException.expect(IllegalArgumentException.class);

        PostDeleteRequestDto postDeleteRequestDto = new PostDeleteRequestDto(1L);
        PostReadRequestDto postReadRequestDto = new PostReadRequestDto(1L);

        PostCreateRequestDto postCreateRequestDto = createPostCreateRequestDto();
        postService.createPost(postCreateRequestDto);

        postService.deletePost(postDeleteRequestDto);

        assertThat(postService.readPost(postReadRequestDto));
    }

    @Test
    public void updatePostTest() {
        PostCreateRequestDto postCreateRequestDto = createPostCreateRequestDto();
        postService.createPost(postCreateRequestDto);


        PostUpdateRequestDto postUpdateRequestDto = createPostUpdateRequestDto(account);

        // When
        PostResponseDto postResponseDto = postService.updatePost(postUpdateRequestDto);

        assertThat(postService.readPost(new PostReadRequestDto(1L)))
                .isEqualToComparingFieldByField(postResponseDto);
    }

    @Test
    public void updatePostFailTest() {
        // expected
        expectedException.expect(PostNotFoundException.class);

        // Given
        PostCreateRequestDto postCreateRequestDto = createPostCreateRequestDto();
        postService.createPost(postCreateRequestDto);

        PostUpdateRequestDto postUpdateRequestDto = createPostUpdateRequestDto(account);
        postUpdateRequestDto.setId(100L);

        // When
        postService.updatePost(postUpdateRequestDto);
    }

    @Test
    public void readPostByPageTest() {
        PostCreateRequestDto postCreateRequestDto = createPostCreateRequestDto();
        postService.createPost(postCreateRequestDto);

        ManyPostRequestDto manyPostRequestDto = ManyPostRequestDto.builder()
                .page(1)
                .build();

        ManyPostResponseDto manyPostResponseDto = postService.readPostByPage(manyPostRequestDto);
        Page<ManyPostResponseDto.PostResponse> postInfoDtoPage = manyPostResponseDto.getPostInfoDtoPage();

        assertThat(postInfoDtoPage.getTotalPages()).isEqualTo(1);
        assertThat(postInfoDtoPage.getTotalElements()).isEqualTo(2);
    }

    private PostCreateRequestDto createPostCreateRequestDto() {
        PostCreateRequestDto postCreateRequestDto = PostCreateRequestDto.builder()
                .accountEmail("testuser@email.com")
                .content("test content")
                .title("test title")
                .build();

        accountRepository.save(account);

        return postCreateRequestDto;
    }

    private PostUpdateRequestDto createPostUpdateRequestDto(Account account) {
        return PostUpdateRequestDto.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .accountEmail("testuser@email.com")
                .password(account.getPassword())
                .build();
    }
}
