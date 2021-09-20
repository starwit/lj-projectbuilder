class MainTheme {
    palette = {
        primary: {
            main: "#009900",
            contrastText: "#fff"
        },
        secondary: {
            main: "#33cc33"
        },
        background: {
            main: "#bdc1c7"
        },
        title: {
            main: "#4c5356"
        }
    };

    card = {
        width: 500
    };

    typography = {
        useNextVariants: true,
        fontFamily: "Arial",
        fontSize: 16,
        button: {
            color: "#fff",
            fontSize: "1rem",
            textTransform: "uppercase",
        },
        body1: {
            color: "#000",
            '&:hover': {
                color: "#fff",
            },
        },
        body2: {
            color: this.palette.title.main,
            lineHeight: 1.7,
        },
        h1: {
            fontSize: "1.8rem",
            textTransform: "uppercase",
            marginBottom: 40,
            marginTop: 20
        },
        h2: {
            textTransform: "uppercase",
            fontSize: "1.2rem",
            marginBottom: 40,
        },
        h5: {
            color: "#fff",
            textTransform: "uppercase",
            fontSize: "1.2rem",
        },
        h6: {
            fontSize: "1.2rem",
            color: this.palette.title.main,
            marginBottom: 5
        }
    };

    shape = {
        borderRadius: 0,
    };

    overrides = {
        MuiDrawer: {
            paper: {
                backgroundColor: this.palette.background.main,
                color: "#fff"
            }
        },
        MuiButton: {
            root: {
                width: 240,
                marginTop: 15,
                marginRight: 15,
                border: "1px solid #fff",
            },
            contained: {
                boxShadow: "none",
                backgroundColor: "none",
                color: "#fff"
            }
        },
        MuiIconButton: {
            root: {
                color: this.palette.primary.main,
            }
        },
        MuiList: {
            root: {
                marginTop: 20,
            }
        },
        MuiListItem: {
            root: {
                '&$selected': {
                    '&&': {
                        backgroundColor: this.palette.secondary.main + " !important"
                    }
                }
            }
        },
        MuiCardHeader: {
            subheader: {
                color: this.palette.background.main
            }
        },
        MuiMenuItem: {
            root: {
                color: this.palette.primary.main,
            }
        },
        MuiFormControl: {
            root: {
                width: 370,
                height: 40,
                left: 0,
                marginRight: 50,
                boxShadow: "none",
                border: 0,
            }
        },
        MuiInput: {
            root: {
                width: 370,
                height: 40,
                marginRight: 20,
                border: "1px solid " + this.palette.background.main,
                borderRadius: 2,
                '&$focused': {
                    border: "1px solid " + this.palette.primary.main,
                },
            },
            underline: {
                '&:before': {
                    borderBottom: 0,
                },
                '&:after': {
                    borderBottom: 0,
                }
            }
        },
        MuiInputLabel: {
            formControl: {
                top: 4,
                left: 16,
                color: this.palette.background.main,
                fontSize: 16,
            },
            shrink: {
                transform: "translate(0, -3px) scale(0.75)",
            }
        },
        MuiInputBase: {
            input: {
                marginLeft: 15,
                color: this.palette.title.main,
            },
        },
        MuiFab: {
            root: {
                borderRadius: 0,
                boxShadow: "none"
            }
        },
        MuiSvgIcon: {
            root: {
                width: "1.2em",
                height: "1.0em"
            }
        }
    }

}

export default MainTheme;
