import {makeStyles} from "@mui/styles";

const EntityDiagramStyles = makeStyles(theme => ({

    addFab: {
        position: "absolute",
        bottom: "5%",
        right: "2%"
    },
    drawer: {
        "width": "50vw",
        "flexShrink": 0,
        "& .MuiDrawer-paper": {
            width: "50vw",
            boxSizing: "border-box"
        }
    },
    draggableWrapper: {
        height: "70vh"
    },
    draggableWrapperDense: {
        height: "20vh"
    },
    statementWrapper: {
        height: "10rem"
    },
    codeButtonWrapper: {
        position: "absolute",
        bottom: "5%",
        left: "2%"
    },
    draggable: {
        position: "absolute",
        width: "20rem",
        zIndex: "200"
    },
    syntaxHighlighterCodeTag: {
        lineHeight: "inherit",
        fontSize: "inherit"
    },
    line: {
        position: "absolute",
        zIndex: "-2",
        borderWidth: "0.25em"
    }

}));
export default EntityDiagramStyles;
