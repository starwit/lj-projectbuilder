import React from "react";
import {
    Alert,
    Card,
    CardActionArea,
    CardContent,
    Typography,
} from "@mui/material";
import { useTranslation } from "react-i18next";

function AppTemplateSelectCard(props) {
    const { template, selected, onSelection } = props;
    const { t } = useTranslation();

    function renderSelectedAlert() {
        if (selected) {
            return (
                <Alert severity={"success"}>{t("app.template.selected")}</Alert>
            );
        }
    }

    return (
        <Card elevation={5}>
            <CardActionArea
                onClick={() => onSelection(template)}
                disabled={selected}
            >
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
