import './App.css';
import { useCookies } from 'react-cookie';
import Register from './components/Register';
import Login from './components/Login';
import Write from './components/Write';

function App() {

  const [cookie, setCookie] = useCookies(['access_token']);

  return (
    <div className="App">
      <Register></Register>
      <Login cookie={cookie} setCookie={setCookie}></Login>
      <Write cookie={cookie} setCookie={setCookie}></Write>
    </div>
  );
}

export default App;
