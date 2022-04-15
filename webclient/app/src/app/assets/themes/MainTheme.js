class MainTheme {
    palette = {
        primary: {
            main: "#de8608",
            contrastText: "#fff"
        },
        secondary: {
            main: "#fff",
            contrastText: "#de8608"
        },
        background: {
            default: "#e6e6e6",
            paper: "#fff"

        },
        line: {
            width: 4
        }
    };

    mixins = {
        toolbar: {
            minHeight: 48
        }
    };

    typography = {
        useNextVariants: true,
        color: "#000",
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
    };

    shape = {
        borderRadius: 0
    };

    overrides = {};

    components = {
        MuiContainer: {
            styleOverrides: {
                root: {
                    padding: 0
                }
            }
        },
        MuiCard: {
            styleOverrides: {
                root: {
                    backgroundColor: "#fff",
                    // eslint-disable-next-line
                    boxShadow: "0px 2px 1px -1px rgb(0 0 0 / 20%), 0px 1px 1px 0px rgb(0 0 0 / 14%), 10px 10px 10px 0px rgb(0 0 0 / 12%)"
                }
            }
        },
        MuiButton: {
            styleOverrides: {
                // Name of the slot
                textPrimary: {
                    fontWeight: 600
                },
                textSecondary: {
                    fontWeight: "medium",
                    fontSize: "1rem"
                },
                containedPrimary: {
                    // eslint-disable-next-line
                    boxShadow: "0px 2px 1px -1px rgb(0 0 0 / 20%), 0px 1px 1px 0px rgb(0 0 0 / 14%), 10px 10px 10px 0px rgb(0 0 0 / 12%)"
                },
                containedSecondary: {
                    color: "#000"
                }
            }
        },
        MuiFab: {
            styleOverrides: {
                // Name of the slot
                primary: {
                    backgroundImage: "linear-gradient(-60deg, rgb(255, 88, 88) 0%, rgb(240, 152, 25) 100%)",
                    // eslint-disable-next-line
                    boxShadow: "0px 2px 1px -1px rgb(0 0 0 / 20%), 0px 1px 1px 0px rgb(0 0 0 / 14%), 10px 10px 10px 0px rgb(0 0 0 / 12%)"
                },
                containedSecondary: {
                    color: "#000"
                }
            }
        }
    };
}

export default MainTheme;
