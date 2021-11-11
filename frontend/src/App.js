import './App.css';
import axios from 'axios';
import { useCookies } from 'react-cookie';

function App() {
  // axios.defaults.withCredentials = true;

  let [cookies, setCookie] = useCookies(['access_token']);

  return (
    <div className="App">
      <button onClick={() => {
        // axios.defaults.withCredentials = true;
        axios.get("http://localhost:8080/api/posts/query?page=0&size=10")
        .then((error) => console.log(error));
      }}>
      getlist
      </button>

      <div>
      <button onClick={() => {
        // axios.defaults.withCredentials = true;
        axios.post("http://localhost:8080/api/register",
          {'email': 'testuser@email.com',
          'name': 'testuser',
          'password': 'pass'
        }
        )
        .then((error) => console.log(error));
      }}>
      register
      </button>
      </div>

      <div>
      <button onClick={() => {
        axios.post("http://localhost:8080/api/login",
          {'email': 'testuser@email.com',
          'name': 'testuser',
          'password': 'pass'
        })
        .then((response) => {
          if(response.status === 200) {
            setCookie('access_token', response.data['access_token']);
          }
          console.log(cookies);
          console.log(response.data['access_token']);
        });
      }}>
      login
      </button>
      </div>
    </div>
  );
}

export default App;
