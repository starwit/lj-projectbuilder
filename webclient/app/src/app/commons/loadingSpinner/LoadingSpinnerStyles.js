import {makeStyles} from "@mui/styles";

const LoadingSpinnerStyles = makeStyles(theme => ({
    root: {
        height: "100%",
        display: "flex",
        justifyContent: "center",
        alignContent: "center"
    },
    contentWrapper: {
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
        alignItems: "center"
    },
    loadingSpinner: {
        marginBottom: theme.spacing(2)
    },
    message: {
        color: theme.palette.text.secondary
    }
}))
export default LoadingSpinnerStyles;