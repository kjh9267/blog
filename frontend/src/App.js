import './App.css';
import { useCookies } from 'react-cookie';
import Register from './components/Register';
import Login from './components/Login';
import Write from './components/Write';
import ArticleList from './components/ArticleList';

function App() {

  const [cookie, setCookie] = useCookies(['access_token']);

  return (
    <div className="App">
      <Register></Register>
      <Login cookie={cookie} setCookie={setCookie}></Login>
      <Write cookie={cookie} setCookie={setCookie}></Write>
      <ArticleList page={0} size={10}></ArticleList>
    </div>
  );
}

export default App;
