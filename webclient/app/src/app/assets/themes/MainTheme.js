class MainTheme {
    palette = {
        primary: {
            main: "#dd8833",
            contrastText: "#fff"
        },
        secondary: {
            main: "#4baef3",
            contrastText: "#fff"
        },
        background: {
            default: "#F5F5F5",
            paper: "#eee"
            
        }
    };

    typography = {
        useNextVariants: true,
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
                    backgroundImage: 'linear-gradient(to top, #cfd9df 0%, #e2ebf0 100%)'
                }
            }
        },
        MuiButton: {
            styleOverrides: {
                // Name of the slot
                root: {
                  // Some CSS
                  variants: [
                    {
                      props: { variant: 'custom' },
                      style: {
                        backgroundColor:'#ffffff',
                        backgroundImage: 'linear-gradient(-60deg, rgb(255, 88, 88) 0%, rgb(240, 152, 25) 100%)'
                      },
                    },
                  ]
                },
                containedPrimary: {
                    backgroundImage: 'linear-gradient(-60deg, rgb(255, 88, 88) 0%, rgb(240, 152, 25) 100%)',
                },
                containedSecondary: {
                    color: '#000'
                }
              },
        }
      };
}

export default MainTheme;
                    