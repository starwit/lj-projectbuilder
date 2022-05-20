import React from "react";
import AppHeader from "./commons/appHeader/AppHeader";
import MainContentRouter from "./pages/MainContentRouter";
import {CssBaseline} from "@mui/material";
import ErrorHandler from "./commons/errorHandler/ErrorHandler";
import {appMenuItems} from "./AppConfig";

function App() {

    return (
        <React.Fragment>
            <ErrorHandler>
                    <CssBaseline/>
                    <AppHeader menuItems={appMenuItems}/>
                    <MainContentRouter/>
            </ErrorHandler>
        </React.Fragment>
    );
}

export default App;
