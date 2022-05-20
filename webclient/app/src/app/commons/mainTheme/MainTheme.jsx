import React from "react";
import {ThemeProvider} from "@mui/material";
import ComponentTheme from "../../assets/themes/ComponentTheme";

function MainTheme(props) {
    return (
            <ThemeProvider theme={ComponentTheme}>
                {props.children}
            </ThemeProvider>
    );
}

export default MainTheme;
