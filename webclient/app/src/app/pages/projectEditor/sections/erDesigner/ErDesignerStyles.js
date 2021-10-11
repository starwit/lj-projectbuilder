import {makeStyles} from "@mui/styles";

const ErDesignerStyles = makeStyles(theme => ({

    entityCard: {
        padding: "1rem",
        width: "20rem"
    },
    addFab: {
        position: "absolute",
        bottom: "5%",
        right: "2%"
    },
    drawer: {
        width: "50vw",
        flexShrink: 0,
        '& .MuiDrawer-paper': {
            width: "50vw",
            boxSizing: 'border-box',
        },
    },
    draggableWrapper: {
        height: "70vh"
    },
    tableRow: {
        '&:last-child td, &:last-child th': {border: 0}
    },
    statementWrapper: {
        height: "10rem"
    }
}))
export default ErDesignerStyles;