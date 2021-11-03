import {makeStyles} from "@mui/styles";

const ErSectionStyles = makeStyles(theme => ({

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
    statementWrapper: {
        height: "10rem"
    },
    codeButtonWrapper: {
        position: "absolute",
        bottom: "5%",
        left: "2%"
    }
}))
export default ErSectionStyles;