import {makeStyles} from "@mui/styles";

const EntityEditorStyles = makeStyles(theme => ({
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
    }
}))
export default EntityEditorStyles;