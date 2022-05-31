import {makeStyles} from "@mui/styles";

const EnumCardStyles = makeStyles(theme => ({
    enumCard: {
        padding: "1rem",
        width: "20rem",
        zIndex: "200",
        backgroundColor: "#ffefd1 !important"
    },
    enumType: {
        color: "#999",
        fontStyle: "italic"
    }
}));
export default EnumCardStyles;
