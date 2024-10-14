package me.jun.core.guestbook.application;

import me.jun.common.event.MemberLeaveEvent;
import me.jun.support.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static me.jun.common.event.EventFixture.memberLeaveEvent;
import static me.jun.core.guestbook.CommentFixture.COMMENT_WRITER_ID;
import static me.jun.core.guestbook.PostFixture.POST_WRITER_ID;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventHandlerTest {

    private EventHandler eventHandler;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @BeforeEach
    void setUp() {
        eventHandler = new EventHandler(postService, commentService);
    }

    @Test
    void handleMemberLeaveEventTest() {
        Event event = memberLeaveEvent();

        eventHandler.handleMemberLeaveEvent((MemberLeaveEvent) event);

        verify(postService).deletePostByWriterId(POST_WRITER_ID);
        verify(commentService).deleteCommentByWriterId(COMMENT_WRITER_ID);
    }
}