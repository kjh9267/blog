import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { CookiesProvider } from 'react-cookie';
import {Provider} from 'react-redux';
import {createStore} from 'redux';

let store = createStore(
  () => {
    return [{data: 1}]
  }
);

ReactDOM.render(
  <CookiesProvider>
    <React.StrictMode>
      <Provider store = {store}>
      <App />
      </Provider>
    </React.StrictMode>
  </CookiesProvider>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
