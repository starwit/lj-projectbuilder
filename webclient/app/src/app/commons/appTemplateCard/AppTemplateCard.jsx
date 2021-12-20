import React from "react";
import { Alert, Collapse, Card, CardContent, CardActions, List, ListItem, ListItemIcon, ListItemText, Typography, Divider, Grid, IconButton } from "@mui/material";
import AppTemplateCardStyles from "./AppTemplateCardStyles";
import { useTranslation } from "react-i18next";
import GitHub from '@mui/icons-material/GitHub';
import { Delete, Edit, ExpandMore } from "@mui/icons-material";
import SyntaxHighlighter from "react-syntax-highlighter/dist/esm/light";
import js from 'react-syntax-highlighter/dist/esm/languages/hljs/javascript';
import dark from 'react-syntax-highlighter/dist/esm/styles/hljs/dark';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

function AppTemplateCard(props) {
    const { appTemplate, selected, handleEdit, handleDelete } = props;
    const appTemplateCardStyles = AppTemplateCardStyles();
    const { t } = useTranslation();
    const [expanded, setExpanded] = React.useState(false);

    SyntaxHighlighter.registerLanguage('javascript', js);

    const appTemplateDetails = `{
        "templateName": "lirejarp",
        "packagePlaceholder": "xyz",
        "templateFiles": [
          {
            "fileName": "\${domain.name}Entity.java",
            "someprop": "some stuff",
            "templatePath": "persistence/Entity.ftl",
            "targetPath": "persistence/src/main/java/de/\${app.packagePrefix?lower_case}/persistence/entity/",
            "category": "ENTITY"
          },
          {
            "fileName": "\${domain.name}Serivce.java",
            "someprop": "some stuff",
            "templatePath": "persistence/Service.ftl",
            "targetPath": "persistence/src/main/java/de/\${app.packagePrefix?lower_case}/persistence/service/",
            "category": "SERVICE"
          },    
          {
            "fileName": "application.properties",
            "templatePath": "application/properties.ftl",
            "targetPath": "application/src/main/resources/",
            "category": "GLOBAL"
          }
        ]
      }`

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

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
            <CardContent className={appTemplateCardStyles.CardContent}>
                <Grid container spacing={0}>
                    <Grid item xs={7}>
                        <Typography gutterBottom variant="h5" component="span">
                            {appTemplate.name}
                        </Typography>
                    </Grid>
                    <Grid item xs={5} align="right">
                        <IconButton onClick={() => handleEdit(appTemplate)}><Edit /></IconButton>
                        <IconButton onClick={() => handleDelete(appTemplate.Id)}><Delete /></IconButton>
                    </Grid>
                </Grid>
                <Divider />
                <Typography variant="body2" color="text.secondary">
                    <br />{appTemplate.description}
                </Typography>
                <List>
                    <ListItem disablePadding>
                        <ListItemIcon>
                            <GitHub />
                        </ListItemIcon>
                        <ListItemText primary={appTemplate.location} secondary={t("templateCard.branch") + ": " + appTemplate.branch} />
                    </ListItem>
                </List>
            </CardContent>
            <CardActions className={appTemplateCardStyles.actionsWrapper}>
                <IconButton onClick={handleExpandClick}>
                    <ExpandMore 
                        expand={expanded}
                        aria-expanded={expanded}
                        aria-label="show more" >
                    <ExpandMoreIcon />
                    </ExpandMore>
                </IconButton>
            </CardActions>
            <Collapse in={expanded} timeout="auto" unmountOnExit>
                <CardContent>
                    <SyntaxHighlighter language="json" style={dark}>
                        {appTemplateDetails}
                    </SyntaxHighlighter>
                </CardContent>
            </Collapse>
        </Card>
    )
}

export default AppTemplateCard;