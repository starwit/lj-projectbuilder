import { makeStyles } from "@mui/styles";

const AddCardStyles = makeStyles(theme => ({
    actionArea: {
        height: "10rem",
        width: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    },
    addIcon: {
        fontSize: "5rem",
        color: theme.palette.text.secondary,
    },
}));
export default AddCardStyles;
