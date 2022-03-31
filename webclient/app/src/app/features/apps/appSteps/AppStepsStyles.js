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
        paddingTop: "2rem"
    },
    listItemText: {
        fontWeight: "bold"
    },
    padding: {
        paddingBottom: "2rem"
    }
}));
export default AppStepsStyles;