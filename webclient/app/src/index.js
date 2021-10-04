import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './app/App';
import * as serviceWorker from './serviceWorker';
import {HashRouter as Router} from "react-router-dom";
import './localization/i18n';
import LoadingSpinner from "./app/commons/loadingSpinner/LoadingSpinner";

ReactDOM.render((
    <Router>
        <App/>
    </Router>
), document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
