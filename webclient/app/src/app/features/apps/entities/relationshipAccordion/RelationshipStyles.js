import {makeStyles} from "@mui/styles";
import AccordionStyles from "../AccordionStyles";

const RelationshipStyles = makeStyles(theme => ({
    ...AccordionStyles(theme),
    spacerBottom: {
        paddingBottom: theme.spacing(2)
    },
    statementWrapper: {
        height: "15rem"
    }
}));
export default RelationshipStyles;
