import {makeStyles} from "@mui/styles";

const TemplateDialogStyles = makeStyles(theme => ({
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
    }
}))
export default TemplateDialogStyles;