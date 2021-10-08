import {makeStyles} from "@mui/styles";

const EntityEditorStyles = makeStyles(theme => ({
    tabBox: {
        borderBottom: 1,
        borderColor: 'divider'
    },
    dialogCloseButton: {
        position: 'absolute',
        right: 8,
        top: 8,
        color: theme.palette.grey[500],
    }
}))
export default EntityEditorStyles;