import './App.css';
import Register from './components/Register';
import Login from './components/Login';
import { useCookies } from 'react-cookie';

function App() {

  const [cookie, setCookie] = useCookies(['access_token']);

  return (
    <div className="App">
      <Register></Register>
      <Login cookie={cookie} setCookie={setCookie}></Login>
    </div>
  );
}

export default App;
