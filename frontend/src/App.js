import './App.css';
import {useCookies} from 'react-cookie';
import JoinController from './blog/presentation/JoinController';
import LoginController from './blog/presentation/LoginController';
import WriteController from './blog/presentation/WriteController';
import ArticlesController from './blog/presentation/ArticlesController';
import CategoriesController from "./blog/presentation/CategoriesController";
import {Link, Route, Routes} from 'react-router-dom';
import CategoryArticlesController from "./blog/presentation/CategoryArticlesController";
import {CATEGORY, QUERY_BLOG_ARTICLES} from "./support/UrlUtils";

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
                <Route exact path='/' element={<ArticlesController url={QUERY_BLOG_ARTICLES}/>}/>
                <Route path='/register' element={<JoinController/>}/>
                <Route path='/login' element={<LoginController setCookie={setCookie}/>}/>
                <Route exact path='/category' element={<CategoriesController url={CATEGORY}/>}/>
                <Route path='/category/:name' element={<CategoryArticlesController/>}/>
                <Route path='/write' element={<WriteController cookie={cookie}/>}/>
            </Routes>
        </div>
    );
}

export default App;
