import {makeStyles} from "@mui/styles";

const GitDataButtonStyles = makeStyles(theme => ({
    tabBox: {
        borderBottom: 1,
        borderColor: 'divider'
    },
    dialogHeaderBar: {
        display: "flex",
        alignContent: "center",
        justifyContent: "space-between"
    },
    statementWrapper: {
        height: "15rem"
    },
    flex: {
        flex: 1
    },

    checkbox: {
        margin: "8px",
        width: "95%"
    }
}))
export default GitDataButtonStyles;