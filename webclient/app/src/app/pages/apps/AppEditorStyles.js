import {makeStyles} from "@mui/styles";

const AppEditorStyles = makeStyles((theme) => ({
    root: {
        paddingTop: theme.spacing(2)
    },
    navigationButtonsArray: {
        display: "flex",
        flexDirection: "row",
        paddingBottom: "2rem"
    },
    navigationButtonBack: {
        mr: 1
    },
    navigationButtonNext: {
        flex: "1 1 auto"
    }
}));
export default AppEditorStyles;
