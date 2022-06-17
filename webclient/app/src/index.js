import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./app/App";
import * as serviceWorker from "./serviceWorker";
import {HashRouter as Router} from "react-router-dom";
import "./localization/i18n";
import {SnackbarProvider} from "notistack";
import MainTheme from "./app/commons/mainTheme/MainTheme";
import {BrowserTracing} from "@sentry/tracing";
import * as Sentry from "@sentry/react";

Sentry.init({
    dsn: "[ENTER DSN KEY HERE!]",
    integrations: [new BrowserTracing()],

    // We recommend adjusting this value in production, or using tracesSampler
    // for finer control
    tracesSampleRate: 1.0,
    enabled: process.env.NODE_ENV !== "development"
});

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
