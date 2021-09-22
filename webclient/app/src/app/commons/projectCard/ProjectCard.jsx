import React from "react";
import {Button, Card, CardActions, CardContent, IconButton, Typography} from "@mui/material";
import {Delete} from "@mui/icons-material";
import PropTypes from "prop-types";

function ProjectCard(props) {
    const {project, onDeleteClick, onEditClick} = props;
    return (
        <Card elevation={5}>
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {project.title}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {project.description}
                </Typography>
            </CardContent>
            <CardActions style={{justifyContent: "flex-end"}}>
                <Button size="small" onClick={onEditClick}>Bearbeiten</Button>
                <IconButton onClick={onDeleteClick}><Delete fontSize={"small"}/></IconButton>
            </CardActions>
        </Card>
    )
}

ProjectCard.propTypes= {
    title: PropTypes.string.isRequired,
    description: PropTypes.string.isRequired,
    onEditClick: PropTypes.func.isRequired,
    onDeleteClick: PropTypes.func.isRequired
}


export default ProjectCard;