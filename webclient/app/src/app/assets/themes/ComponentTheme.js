import {createTheme} from "@mui/material";
import ColorTheme from "./ColorTheme";

const ComponentTheme = createTheme(ColorTheme,
    {
        components: {
            MuiContainer: {},
            MuiCard: {
                defaultProps: {
                    elevation: 10
                }
            },
            MuiFab: {
                defaultProps: {
                    color: "secondary"
                }
            },
            MuiStack: {
                defaultProps: {
                    spacing: 2
                }
            }
        }
    });
export default ComponentTheme;