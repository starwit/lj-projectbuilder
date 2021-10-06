import React from "react";
import {Alert, Button, Card, CardActions, CardContent, CardMedia, Typography} from "@mui/material";


function TemplateCard(props) {
    const {template, selected, onSelection} = props;

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
                style={{backgroundColor: "#000"}}
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
            <CardActions style={{justifyContent: "flex-end"}}>
                <Button size="small" disabled={selected} onClick={() => onSelection(template)}>Auswählen</Button>
            </CardActions>
        </Card>
    )

}

export default TemplateCard;