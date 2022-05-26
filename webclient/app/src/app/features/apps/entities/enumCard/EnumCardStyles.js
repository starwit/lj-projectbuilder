import {makeStyles} from "@mui/styles";
import {color} from "@mui/system";

const EnumCardStyles = makeStyles(theme => ({
    enumCard: {
        padding: "1rem",
        width: "20rem",
        zIndex: "200",
        backgroundColor: "#ffefd1 !important"
    },
    enumType: {
        color: "#999999 !important"
    }
}));
export default EnumCardStyles;
