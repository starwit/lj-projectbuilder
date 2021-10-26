import React from "react";
import {Alert, Button, Card, CardActions, CardContent, CardMedia, Typography} from "@mui/material";
import TemplateCardStyles from "./TemplateCardStyles";


function TemplateCard(props) {
    const {template, selected, onSelection} = props;
    const templateCardStyles = TemplateCardStyles();

    function renderSelectedAlert(){
        if (selected) {
            return (
                <Alert severity={"success"}>Ausgewählt</Alert>
            )
        }
    }

    return (
        <Card color={"error"}>
            <CardMedia
                component="img"
                height="150rem"
                image={template.image}
                alt="green iguana"
                className={templateCardStyles.cardMedia}
            />
            {renderSelectedAlert()}
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {template.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {template.description}
                </Typography>
            </CardContent>
            <CardActions className={templateCardStyles.actionsWrapper}>
                <Button size="small" disabled={selected} onClick={() => onSelection(template)}>Auswählen</Button>
            </CardActions>
        </Card>
    )

}

export default TemplateCard;