import React from "react";
import AppHeader from "./commons/appHeader/AppHeader";
import MainContentRouter from "./pages/MainContentRouter";
import {CssBaseline} from "@mui/material";
import AppStyles from "./AppStyles";
import ErrorHandler from "@starwit/react-starwit";
import {appMenuItems} from "./AppConfig";

function App() {
    const appStyles = AppStyles();

    return (
        <React.Fragment>
            <ErrorHandler>
                <div className={appStyles.background}>
                    <CssBaseline/>
                    <AppHeader menuItems={appMenuItems}/>
                    <MainContentRouter/>
                </div>
            </ErrorHandler>
        </React.Fragment>
    );
}

export default App;
