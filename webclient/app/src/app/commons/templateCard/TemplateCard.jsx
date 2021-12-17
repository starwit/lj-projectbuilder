import React from "react";
import { Alert, Button, Card, CardActions, CardContent, List, ListItem, ListItemIcon, ListItemText, Typography } from "@mui/material";
import TemplateCardStyles from "./TemplateCardStyles";
import { useTranslation } from "react-i18next";
import GitHub from '@mui/icons-material/GitHub';

function TemplateCard(props) {
    const { template, selected, onSelection } = props;
    const templateCardStyles = TemplateCardStyles();
    const { t } = useTranslation();

    function renderSelectedAlert() {
        if (selected) {
            return (
                <Alert severity={"success"}>{t("templateCard.selected")}</Alert>
            )
        }
    }

    return (
        <Card color={"error"}>
            {renderSelectedAlert()}
            <CardContent>
                <Typography gutterBottom variant="h5" component="div">
                    {template.name}
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    {template.description}
                </Typography>
                <List>
                    <ListItem disablePadding>
                        <ListItemIcon>
                            <GitHub />
                        </ListItemIcon>
                        <ListItemText primary={template.location} secondary={t("templateCard.branch") + ": " + template.branch}/>
                    </ListItem>
                </List>
            </CardContent>
            <CardActions className={templateCardStyles.actionsWrapper}>
                <Button size="small" disabled={true}
                    onClick={() => onSelection(template)}>{t("templateCard.select")}</Button>
            </CardActions>
        </Card>
    )

}

export default TemplateCard;