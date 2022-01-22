import './App.css';
import {useCookies} from 'react-cookie';
import Register from './components/Register';
import Login from './components/Login';
import Write from './components/Write';
import ArticleList from './components/ArticleList';
import Category from "./components/Category";
import {Link, Route, Routes} from 'react-router-dom';

function App() {

    const [cookie, setCookie] = useCookies(['access_token']);

    return (
        <div className="App">
            <div className="navbar">
                <div className="nav-element">
                    <Link to='/'>
                        <h3>home</h3>
                    </Link>
                </div>
                <div className="nav-element">
                    <Link to='/register'>
                        <h3>register</h3>
                    </Link>
                </div>
                <div className="nav-element">
                    <Link to='/login'>
                        <h3>login</h3>
                    </Link>
                </div>
                <div className="nav-element">
                    <Link to="/category">
                        <h3>Category</h3>
                    </Link>
                </div>
                <div className="nav-element">
                    <Link to='/write'>
                        <h3>write</h3>
                    </Link>
                </div>
            </div>
            <Routes>
                <Route exact path='/' element={<ArticleList></ArticleList>}/>
                <Route path='/register' element={<Register></Register>}/>
                <Route path='/login' element={<Login cookie={cookie} setCookie={setCookie}></Login>}/>
                <Route path='/category' element={<Category></Category>}/>
                <Route path='/write' element={<Write cookie={cookie} setCookie={setCookie}></Write>}/>
            </Routes>
        </div>
    );
}

export default App;
