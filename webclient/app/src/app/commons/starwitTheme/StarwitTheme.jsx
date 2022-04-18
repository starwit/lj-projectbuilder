import React from "react";
import {createTheme, ThemeProvider} from "@mui/material";
import Theme from "../../assets/themes/Theme";

function MainTheme(props) {
    // theme settings
    const theme = createTheme(new Theme());

    return (
        <ThemeProvider theme={theme}>
            {props.children}
        </ThemeProvider>
    );
}

export default MainTheme;
