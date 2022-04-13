import {makeStyles} from "@mui/styles";

const StatementStyles = makeStyles(theme => ({

    root: props => ({
        display: "flex",
        alignContent: "center",
        justifyContent: "center",
        height: props.height,
        width: "100%"
    }),
    content: {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column"
    },
    message: {
        color: theme.palette.text.secondary,
        paddingTop: theme.spacing(2)
    }

}));
export default StatementStyles;
