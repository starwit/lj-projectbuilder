import {makeStyles} from "@mui/styles";

const AppCardStyles = makeStyles((theme) => ({
    cardActions: {
        justifyContent: "flex-end"
    },
    description: {
        color: theme.palette.text.secondary
    }
}));
export default AppCardStyles;
