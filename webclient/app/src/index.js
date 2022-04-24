import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./app/App";
import * as serviceWorker from "./serviceWorker";
import {HashRouter as Router} from "react-router-dom";
import "./localization/i18n";
import {SnackbarProvider} from "notistack";
import MainTheme from "./app/commons/mainTheme/MainTheme";

ReactDOM.render((
    <Router>
        <MainTheme>
            <SnackbarProvider maxSnack={5}>
                <App/>
            </SnackbarProvider>
        </MainTheme>
    </Router>
),
document.getElementById("root")
)
;

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: http://bit.ly/CRA-PWA
serviceWorker.unregister();
