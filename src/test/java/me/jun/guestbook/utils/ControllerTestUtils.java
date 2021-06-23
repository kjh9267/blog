package me.jun.guestbook.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class ControllerTestUtils {

    public static final String LINKS_SELF_HREF = "_links.self.href";

    public static final String LINKS_CREATE_POST_HREF = "_links.create_post.href";

    public static final String LINKS_GET_POST_HREF = "_links.get_post.href";

    public static final String LINKS_UPDATE_POST_HREF = "_links.update_post.href";

    public static final String LINKS_DELETE_POST_HREF = "_links.delete_post.href";

    public static final String LINKS_CREATE_COMMENT_HREF = "_links.create_comment.href";

    public static final String LINKS_GET_COMMENT_HREF = "_links.get_comment.href";

    public static final String LINKS_UPDATE_COMMENT_HREF = "_links.update_comment.href";

    public static final String LINKS_DELETE_COMMENT_HREF = "_links.delete_comment.href";

    public static final String QUERY_COMMENTS_BY_POST_HREF = "_links.query_comments_by_post_id.href";

    public static final String POSTS_SELF_URI = "http://localhost/api/posts";

    public static final String COMMENTS_SELF_URI = "http://localhost/api/comments";

    public static final String QUERY_COMMENTS_BY_POST_URI = "http://localhost/api/comments/query/post-id";
}
