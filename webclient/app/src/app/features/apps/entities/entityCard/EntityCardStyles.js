import {makeStyles} from "@mui/styles";

const EntityCardStyles = makeStyles(theme => ({
    statementWrapper: {
        height: "10rem"
    },
    tableRow: {
        "&:last-child td, &:last-child th": {border: 0}
    },
    entityCard: {
        padding: "1rem",
        width: "20rem",
        zIndex: "200"
    },
    relationshipPlaceholder: {
        width: "1px"
    }
}));
export default EntityCardStyles;
