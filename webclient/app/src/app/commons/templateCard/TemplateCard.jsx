import React from "react";
import { Alert, Collapse, Card, CardContent, CardActions, List, ListItem, ListItemIcon, ListItemText, Typography, Divider, Grid, IconButton } from "@mui/material";
import TemplateCardStyles from "./TemplateCardStyles";
import { useTranslation } from "react-i18next";
import GitHub from '@mui/icons-material/GitHub';
import { Delete, Edit, ExpandMore } from "@mui/icons-material";
import SyntaxHighlighter from "react-syntax-highlighter/dist/esm/light";
import js from 'react-syntax-highlighter/dist/esm/languages/hljs/javascript';
import dark from 'react-syntax-highlighter/dist/esm/styles/hljs/dark';


function TemplateCard(props) {
    const { template, selected } = props;
    const templateCardStyles = TemplateCardStyles();
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
            <CardContent className={templateCardStyles.CardContent}>
                <Grid container spacing={0}>
                    <Grid item xs={7}>
                        <Typography gutterBottom variant="h5" component="span">
                            {template.name}
                        </Typography>
                    </Grid>
                    <Grid item xs={5} align="right">
                        <IconButton><Edit /></IconButton>
                        <IconButton><Delete /></IconButton>
                    </Grid>
                </Grid>
                <Divider />
                <Typography variant="body2" color="text.secondary">
                    <p />{template.description}
                </Typography>
                <List>
                    <ListItem disablePadding>
                        <ListItemIcon>
                            <GitHub />
                        </ListItemIcon>
                        <ListItemText primary={template.location} secondary={t("templateCard.branch") + ": " + template.branch} />
                    </ListItem>
                </List>
            </CardContent>
            <CardActions className={templateCardStyles.actionsWrapper}>
                <IconButton>
                    <ExpandMore 
                          expand={expanded}
                          onClick={handleExpandClick}
                          aria-expanded={expanded}
                          aria-label="show more" />
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

export default TemplateCard;