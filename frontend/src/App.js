import './App.css';
import {useCookies} from 'react-cookie';
import RegisterController from './blog/presentation/RegisterController';
import LoginController from './blog/presentation/LoginController';
import WriteController from './blog/presentation/WriteController';
import ArticleListController from './blog/presentation/ArticleListController';
import CategoryListController from "./blog/presentation/CategoryListController";
import {Link, Route, Routes} from 'react-router-dom';
import CategoryArticleListController from "./blog/presentation/CategoryArticleListController";
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
                <Route exact path='/' element={<ArticleListController url={QUERY_BLOG_ARTICLES}/>}/>
                <Route path='/register' element={<RegisterController/>}/>
                <Route path='/login' element={<LoginController setCookie={setCookie}/>}/>
                <Route exact path='/category' element={<CategoryListController url={CATEGORY}/>}/>
                <Route path='/category/:name' element={<CategoryArticleListController/>}/>
                <Route path='/write' element={<WriteController cookie={cookie}/>}/>
            </Routes>
        </div>
    );
}

export default App;
