import React from "react";

import MainTheme from "./assets/themes/MainTheme";

import AppHeader from "./commons/header/AppHeader";
import MainContentRouter from "./pages/MainContentRouter";
import AppStyles from "./AppStyles";
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";


function App() {
    const appStyles = AppStyles();


    // theme settings
    const theme = createTheme(new MainTheme());

    return (
        <React.Fragment>
            <ThemeProvider theme={theme}>
                    <div>
                        <CssBaseline/>
                        <AppHeader/>
                        <MainContentRouter/>
                    </div>
            </ThemeProvider>
        </React.Fragment>
    );

}

export default App;