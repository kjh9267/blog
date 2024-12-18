const HOST = 'http://localhost:8080';

const JOIN = HOST + '/api/member/register';

const LOGIN = HOST + '/api/member/login';

const DISPLAY = HOST + '/api/display'

const BLOG_ARTICLE = HOST + '/api/blog/articles';

const QUERY_BLOG_ARTICLES = HOST + '/api/blog/articles/query?';

const CATEGORY = HOST + '/api/blog/category';

const GUESTBOOK_POST = HOST + '/api/guestbook/posts';

const GUESTBOOK_COMMENT = HOST + '/api/guestbook/comments';


export {
    HOST,
    JOIN,
    LOGIN,
    DISPLAY,
    BLOG_ARTICLE,
    GUESTBOOK_POST,
    CATEGORY,
    GUESTBOOK_COMMENT,
    QUERY_BLOG_ARTICLES
};