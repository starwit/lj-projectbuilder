import {makeStyles} from "@mui/styles";

const RelationshipStyles = makeStyles(theme => ({
    spacerBottom: {
        paddingBottom: theme.spacing(2)
    },
    title: {
        flexShrink: 0,
        width: "50%"
    },
    subtitle: {
        color: theme.palette.text.secondary
    }
}));
export default RelationshipStyles;