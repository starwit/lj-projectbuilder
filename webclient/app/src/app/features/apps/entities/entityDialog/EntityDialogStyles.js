import {makeStyles} from "@mui/styles";

const EntityDialogStyles = makeStyles(theme => ({
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
    tabHeader: {
        paddingBottom: theme.spacing(2)
    }
}));
export default EntityDialogStyles;
