package me.jun.guestbook.application;

import me.jun.guestbook.dto.*;
import me.jun.guestbook.application.exception.PostNotFoundException;
import me.jun.guestbook.domain.account.Account;
import me.jun.guestbook.domain.account.AccountRepository;
import me.jun.guestbook.domain.post.Post;
import me.jun.guestbook.domain.post.PostRepository;
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
        PostRequest dto = PostRequest.builder()
                .id(1L)
                .build();

        accountRepository.save(account);
        Post savedPost = postRepository.save(post);
        PostResponse postResponse = PostResponse.of(savedPost, account);

        assertThat(postService.readPost(dto))
                .isEqualToComparingFieldByField(postResponse);
    }

    @Test
    public void createPostTest() {
        PostRequest postReadRequestDto = PostRequest.builder()
                .id(1L)
                .build();

        PostRequest postRequest = createPostCreateRequestDto();
        postService.createPost(postRequest);

        assertThat(postService.readPost(postReadRequestDto))
                .isEqualToIgnoringGivenFields(postRequest,"id", "accountEmail", "accountName");
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void deletePostTest() {
        expectedException.expect(PostNotFoundException.class);

        PostRequest deleteDto = PostRequest.builder()
                .id(1L)
                .build();
        PostRequest readDto = PostRequest.builder()
                .id(1L)
                .build();

        PostRequest postRequest = createPostCreateRequestDto();
        postService.createPost(postRequest);

        postService.deletePost(deleteDto);

        assertThat(postService.readPost(readDto));
    }

    @Test
    public void updatePostTest() {
        PostRequest postRequest = createPostCreateRequestDto();
        postService.createPost(postRequest);

        PostRequest postUpdateRequestDto = createPostUpdateRequestDto();

        // When
        PostResponse postResponse = postService.updatePost(postUpdateRequestDto);

        assertThat(postService.readPost(PostRequest.builder().id(1L).build()))
                .isEqualToComparingFieldByField(postResponse);
    }

    @Test
    public void updatePostFailTest() {
        // expected
        expectedException.expect(PostNotFoundException.class);

        // Given
        PostRequest createDto = createPostCreateRequestDto();
        postService.createPost(createDto);

        PostRequest updateDto = PostRequest.builder()
                .id(100L)
                .title("new title")
                .content("new content")
                .build();

        // When
        postService.updatePost(updateDto);
    }

    @Test
    public void readPostByPageTest() {
        PostRequest postRequest = createPostCreateRequestDto();
        postService.createPost(postRequest);

        ManyPostRequestDto manyPostRequestDto = ManyPostRequestDto.builder()
                .page(1)
                .build();

        ManyPostResponseDto manyPostResponseDto = postService.readPostByPage(manyPostRequestDto);
        Page<ManyPostResponseDto.PostResponse> postInfoDtoPage = manyPostResponseDto.getPostInfoDtoPage();

        assertThat(postInfoDtoPage.getTotalPages()).isEqualTo(1);
        assertThat(postInfoDtoPage.getTotalElements()).isEqualTo(2);
    }

    private PostRequest createPostCreateRequestDto() {
        PostRequest postRequest = PostRequest.builder()
                .content("test content")
                .title("test title")
                .build();

        accountRepository.save(account);

        return postRequest;
    }

    private PostRequest createPostUpdateRequestDto() {
        return PostRequest.builder()
                .id(1L)
                .title("new title")
                .content("new content")
                .build();
    }
}
