import React from "react";
import { useCookies } from "react-cookie";
import axios from "axios";

function TestComponent() {

    let [cookies, setCookie] = useCookies(['access_token']);

    return(
        <div>

<button onClick={() => {
        axios.get("http://localhost:8080/api/guestbook/posts/query?page=0&size=10")
        .then((response) => console.log(response.data));
      }}>
      get posts list
      </button>

      <div>
      <button onClick={() => {
        axios.post("http://localhost:8080/api/member/register",
          {'email': 'testuser@email.com',
          'name': 'testuser',
          'password': 'pass'
        }
        )
        .then((response) => console.log(response.data));
      }}>
      register
      </button>
      </div>

      <div>
      <button onClick={() => {
        axios.post("http://localhost:8080/api/member/login",
          {'email': 'testuser@email.com',
          'name': 'testuser',
          'password': 'pass'
        })
        .then((response) => {
          console.log(response.status);
          if(response.status === 200) {
            setCookie('access_token', response.data['access_token']);
          }
          console.log(response.data['access_token']);
          console.log(cookies);
        });
      }}>
      login
      </button>
      </div>

      <div>
      <button onClick={() => {
        console.log(cookies['access_token']);
        axios.post("http://localhost:8080/api/guestbook/posts",
          {'title': 'test title',
          'content': 'test content'
        },
        {headers: {'Authorization': cookies['access_token']}}
        )
        .then((response) => console.log(response.data));
      }}>
      create post
      </button>
      </div>

      <div>
      <button onClick={() => {
        console.log(cookies['access_token']);
        axios.get("http://localhost:8080/api/guestbook/posts/1",
        )
        .then((response) => console.log(response.data));
      }}>
      get post
      </button>
      </div>

      <div>
      <button onClick={() => {
        console.log(cookies['access_token']);
        axios.put("http://localhost:8080/api/guestbook/posts",
          {'id': 1,
            'title': 'new title',
            'content': 'new content'
        },
        {headers: {'Authorization': cookies['access_token']}}
        )
        .then((response) => console.log(response.data));
      }}>
      update post
      </button>
      </div>

      <div>
      <button onClick={() => {
        console.log(cookies['access_token']);
        axios.delete("http://localhost:8080/api/guestbook/posts/1",
        {headers: {'Authorization': cookies['access_token']}}
        )
        .then((response) => console.log(response.data));
      }}>
      delete post
      </button>
      </div>

      <div>
      <button onClick={() => {
        axios.get("http://localhost:8080/api/guestbook/comments/query/post-id/1?page=0&size=10",
        )
        .then((response) => console.log(response.data));
      }}>
      get comments list
      </button>
      </div>

      <div>
      <button onClick={() => {
        console.log(cookies['access_token']);
        axios.post("http://localhost:8080/api/guestbook/comments",
          {'postId': 1,
            'content': 'test content'
        },
        {headers: {'Authorization': cookies['access_token']}}
        )
        .then((response) => console.log(response.data));
      }}>
      create comment
      </button>
      </div>

        </div>
    )
}


export default TestComponent;