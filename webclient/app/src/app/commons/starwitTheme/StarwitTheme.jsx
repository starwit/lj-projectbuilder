import React from "react";
import { createTheme, ThemeProvider } from "@mui/material";
import MainTheme from "../../assets/themes/MainTheme";

function StarwitTheme(props) {
    // theme settings
    const theme = createTheme(new MainTheme());

    return <ThemeProvider theme={theme}>{props.children}</ThemeProvider>;
}

export default StarwitTheme;
