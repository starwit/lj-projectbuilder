import {createTheme} from "@mui/material";

const ColorTheme = createTheme({
    palette: {
        type: "light",
        primary: {
            main: "#0B3954",
            contrastText: "#fff"
        },
        secondary: {
            main: "#FF8552",
            contrastText: "#000"
        },
        background: {
            default: "#f1f1f1",
            paper: "#fff"
        },
        line: {
            width: 4
        }
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


})

export default ColorTheme;
