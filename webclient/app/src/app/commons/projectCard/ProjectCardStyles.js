import {makeStyles} from "@mui/styles";

const ProjectCardStyles = makeStyles(theme => ({
    cardActions: {
        justifyContent: "flex-end"
    },
    description: {
        color: theme.palette.text.secondary
    }
}))
export default ProjectCardStyles;