import {makeStyles} from "@mui/styles";

const GitDataButtonStyles = makeStyles(theme => ({
    tabBox: {
        borderBottom: 1,
        borderColor: "divider"
    },
    dialogHeaderBar: {
        display: "flex",
        alignContent: "center",
        justifyContent: "space-between"
    },
    flex: {
        flex: 1
    },

    checkbox: {
        margin: theme.spacing(4)
    }
}));
export default GitDataButtonStyles;
