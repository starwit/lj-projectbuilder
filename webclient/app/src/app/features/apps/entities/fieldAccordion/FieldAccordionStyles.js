import {makeStyles} from "@mui/styles";

const FieldAccordionStyles = makeStyles(theme => ({
    title: {
        flexShrink: 0,
        width: "50%"
    },
    subtitle: {
        color: theme.palette.text.secondary
    }
}));
export default FieldAccordionStyles;
