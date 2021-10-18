import React from "react";
import {Button, Card, CardActionArea, CardActions, CardContent, IconButton, Typography} from "@mui/material";
import {Delete} from "@mui/icons-material";
import PropTypes from "prop-types";
import {useHistory} from "react-router-dom";
import AppCardStyles from "./AppCardStyles";

function AppCard(props) {

    const {app, onDeleteClick, onEditClick} = props;
    const history = useHistory();
    const appCardStyles = AppCardStyles()

    return (
        <Card elevation={5}>
            <CardActionArea onClick={() => history.push("/app/" + app.id)}>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {app.title}
                    </Typography>
                    <Typography variant="body2" className={appCardStyles.description}>
                        {app.description}
                    </Typography>
                </CardContent>
            </CardActionArea>
            <CardActions className={appCardStyles.cardActions}>
                <Button size="small" onClick={onEditClick}>Bearbeiten</Button>
                <IconButton onClick={onDeleteClick}><Delete fontSize={"small"}/></IconButton>
            </CardActions>
        </Card>
    )
}

AppCard.propTypes = {
    app: PropTypes.shape({
        id: PropTypes.number.isRequired,
        title: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired
    }),
    onEditClick: PropTypes.func.isRequired,
    onDeleteClick: PropTypes.func.isRequired
}


export default AppCard;