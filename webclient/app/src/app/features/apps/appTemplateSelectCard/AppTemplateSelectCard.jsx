import React from "react";
import {Alert, Card, CardActionArea, CardContent, CardMedia, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import DefaultTemplateImage from "../../../assets/images/DefaultAppTemplate.jpg";

function AppTemplateSelectCard(props) {
    const {template, selected, onSelection} = props;
    const {t} = useTranslation();

    function renderSelectedAlert() {
        if (selected) {
            return (
                <Alert severity={"success"}>{t("app.template.selected")}</Alert>
            );
        }
    }

    function getTemplateUrl() {
        if (template.imageUrl) {
            return template.imageUrl;
        }

        return DefaultTemplateImage;
    }

    return (
        <Card elevation={5}>
            <CardMedia
                component="img"
                height="140"
                image={getTemplateUrl()}
                alt={"preview image of " + template.name}
            />
            <CardActionArea onClick={() => onSelection(template)} disabled={selected}>
                <CardContent>
                    <Typography gutterBottom variant="h5" component="div">
                        {template.name}
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {template.description}
                    </Typography>
                </CardContent>
                {renderSelectedAlert()}
            </CardActionArea>
        </Card>
    );
}

export default AppTemplateSelectCard;
