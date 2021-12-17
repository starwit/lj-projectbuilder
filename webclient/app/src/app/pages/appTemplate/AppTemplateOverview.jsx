import React from "react";
import { Container,Typography } from "@mui/material";
import {useTranslation} from "react-i18next";
import { Grid } from "@mui/material";
import TemplateCard from "../../commons/templateCard/TemplateCard";
import { useHistory } from "react-router";

function AppTemplateOverview(props) {
    const history = useHistory();
    const {t} = useTranslation();

    const appTemplates = [
        {
            "id": 1,
            "name": "lirejarp",
            "location": "https://github.com/starwit/project-templates.git",
            "branch": "v2",
            "credentialsRequired": false,
            "description": "LireJarp@Spring - github master template",
            "packagePlaceholder": "xyz"
          },
          {
            "id": 2,
            "name": "asfddaf",
            "location": "https://github.com/starwit/lirejarp.git",
            "branch": "v2",
            "credentialsRequired": false,
            "description": "LireJarp@Spring - the old one",
            "packagePlaceholder": "starwit"
          }
      ];
      
    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("appTemplateOverview.title")}
            </Typography>
            <Grid container spacing={5}>
                {appTemplates.map(appTemplate => (
                    <Grid item sm={6}>
                        <TemplateCard
                            template={appTemplate}/>
                    </Grid>
                ))}
           </Grid>
        </Container>
    )
}

export default AppTemplateOverview