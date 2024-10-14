package me.jun.core.guestbook.application;

import lombok.RequiredArgsConstructor;
import me.jun.common.event.MemberLeaveEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class EventHandler {

    private final PostService postService;

    private final CommentService commentService;

    @EventListener(MemberLeaveEvent.class)
    public void handleMemberLeaveEvent(MemberLeaveEvent event) {
        Long id = event.getId();
        postService.deletePostByWriterId(id);
        commentService.deleteCommentByWriterId(id);
    }
}
