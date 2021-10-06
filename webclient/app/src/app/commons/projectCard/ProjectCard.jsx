import React from "react";
import {Button, Card, CardActionArea, CardActions, CardContent, IconButton, Typography} from "@mui/material";
import {Delete} from "@mui/icons-material";
import PropTypes from "prop-types";
import {useHistory} from "react-router-dom";

function ProjectCard(props) {

    const {project, onDeleteClick, onEditClick} = props;
    const history = useHistory();

    return (
        <Card elevation={5}>
            <CardActionArea onClick={() => history.push("/project/"+project.id)}>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {project.title}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {project.description}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions style={{justifyContent: "flex-end"}}>
                <Button size="small" onClick={onEditClick}>Bearbeiten</Button>
                <IconButton onClick={onDeleteClick}><Delete fontSize={"small"}/></IconButton>
            </CardActions>
        </Card>
    )
}

ProjectCard.propTypes = {
    project: PropTypes.shape({
       id: PropTypes.number.isRequired,
       title: PropTypes.string.isRequired,
       description: PropTypes.string.isRequired
    }),
    onEditClick: PropTypes.func.isRequired,
    onDeleteClick: PropTypes.func.isRequired
}


export default ProjectCard;