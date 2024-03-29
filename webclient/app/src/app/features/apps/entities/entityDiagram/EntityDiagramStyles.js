import {makeStyles} from "@mui/styles";

const EntityDiagramStyles = makeStyles(theme => ({

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
    codeButtonWrapper: {
        position: "absolute",
        bottom: "5%",
        left: "2%"
    },
    centerButtonWrapper: {
        position: "absolute",
        bottom: "12%",
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
