import React from "react";

import MainTheme from "./assets/themes/MainTheme";

import AppHeader from "./commons/appHeader/AppHeader";
import MainContentRouter from "./pages/MainContentRouter";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import AppStyles from "./AppStyles";


function App() {

    const appStyles = AppStyles();

    // theme settings
    const theme = createTheme(new MainTheme());

    return (
        <React.Fragment>
            <ThemeProvider theme={theme}>
                <div className={appStyles.background} > 
                    <CssBaseline/>
                    <AppHeader/>
                    <MainContentRouter />
                </div>
            </ThemeProvider>
        </React.Fragment>
    );

}

export default App;