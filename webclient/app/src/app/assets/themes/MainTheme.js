const MainTheme = {
    palette: {
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
    },

    mixins: {
        toolbar: {
            minHeight: 48
        }
    },

    typography: {
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
    },

    shape: {
        borderRadius: 0
    },

    overrides: {},

    components: {
        MuiContainer: {
            styleOverrides: {
                root: {
                    padding: 0
                }
            }
        },
        MuiCard: {
            defaultProps: {
                elevation: 10
            },
            styleOverrides: {
                root: {
                    backgroundColor: "#fff"
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
                containedSecondary: {
                    color: "#000"
                }
            }
        },
        MuiFab: {
            styleOverrides: {
                // Name of the slot
                primary: {
                    backgroundImage: "linear-gradient(-60deg, rgb(255, 88, 88) 0%, rgb(240, 152, 25) 100%)"
                },
                containedSecondary: {
                    color: "#000"
                }
            }
        }
    }
}

export default MainTheme;
