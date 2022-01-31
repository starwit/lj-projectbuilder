class MainTheme {
    palette = {
        primary: {
            main: "#de8608",
            contrastText: "#fff"
        },
        secondary: {
            main: "#fff",
            contrastText: "#fff"
        },
        background: {
            default: "#c7c7c7",
            paper: "#eee"
            
        }
    };

    typography = {
        useNextVariants: true,
        color: "#000",
        fontFamily: "Arial",
        fontSize: 16
    };

    shape = {
        borderRadius: 0,
    };

    overrides = {};

    components = {
        MuiCard: {
            styleOverrides: {
                root: {
                    backgroundImage: 'linear-gradient(to top, rgb(235 235 235), rgb(255 255 255))',
                    boxShadow: '0px 2px 1px -1px rgb(0 0 0 / 20%), 0px 1px 1px 0px rgb(0 0 0 / 14%), 10px 10px 10px 0px rgb(0 0 0 / 12%)'
                }
            }
        },
        MuiButton: {
            styleOverrides: {
                // Name of the slot
                textPrimary: {
                    fontWeight: 600
                },
                containedPrimary: {
                    boxShadow: '0px 2px 1px -1px rgb(0 0 0 / 20%), 0px 1px 1px 0px rgb(0 0 0 / 14%), 10px 10px 10px 0px rgb(0 0 0 / 12%)',
                },
                containedSecondary: {
                    color: '#000'
                }
              },
        },
        MuiFab: {
            styleOverrides: {
                // Name of the slot
                primary: {
                    backgroundImage: 'linear-gradient(-60deg, rgb(255, 88, 88) 0%, rgb(240, 152, 25) 100%)',
                    boxShadow: '0px 2px 1px -1px rgb(0 0 0 / 20%), 0px 1px 1px 0px rgb(0 0 0 / 14%), 10px 10px 10px 0px rgb(0 0 0 / 12%)',
                },
                containedSecondary: {
                    color: '#000'
                }
              },
        }
      };
}


export default MainTheme;
                    