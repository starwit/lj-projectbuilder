import {makeStyles} from "@mui/styles";

const AppStepsStyles = makeStyles(theme => ({
    downloadGrid: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        width: "100%"
    },
    list: {
        width: "100%"
    },
    root: {
        paddingTop: theme.spacing(2)
    },
    listItemText: {
        fontWeight: "bold"
    }
}));
export default AppStepsStyles;
