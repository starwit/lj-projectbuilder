import {createTheme} from "@mui/material";

const ColorThemeNight = createTheme({
    palette: {
        type: "dark",
        primary: {
            main: "#212121",
            contrastText: "#fff"
        },
        secondary: {
            main: "#FF8552",
            contrastText: "#000"
        },
        background: {
            default: "#121212",
            paper: "#212121"
        },
        line: {
            width: 4
        },
        text: {
            primary: "#fff",
            secondary: "rgba(255,255,255, 0.7)",
            disabled: "rgba(255,255,255, 0.5)",
            icon: "rgba(255,255,255, 0.5)"
        },
        divider: "rgba(255,255,255, 0.12)"

    },

    mixins: {
        toolbar: {
            minHeight: 48
        }
    },

    typography: {
        useNextVariants: true,
        fontFamily: "Lato, sans-serif",
        fontSize: 16,
        body1: {},
        body2: {},
        h1: {
            fontSize: "2rem",
            fontWeight: 400,
            textTransform: "uppercase"
        },
        h2: {
            fontSize: "2rem",
            fontWeight: 400,
            textTransform: "uppercase"
        },
        h5: {
            fontSize: "1.2rem"
        },
        h6: {
            fontSize: "1.2rem",
            marginBottom: 5
        }
    },

    shape: {
        borderRadius: 0
    },

    overrides: {}


});

export default ColorThemeNight;
