import { Container, Grid, Typography } from "@mui/material";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import TemplateCard from "../../commons/appTemplateCard/AppTemplateCard";
import AppTemplateDialog from "../../commons/appTemplateDialog/AppTemplateDialog";

function AppTemplateOverview(props) {
    const { t } = useTranslation();
    const [currentAppTemplate, setCurrentAppTemplate] = useState(false);
    const [data, setData] = useState({
        appTemplates: [
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
        ]
    });


    function deleteAppTemplate(appTemplateId) {
        const foundIndex = data.appTemplates.findIndex(appTemplate => appTemplate.id === appTemplateId)

        const newData = { ...data }
        if (foundIndex > -1) {
            newData.appTemplates.splice(foundIndex, 1);
        }
        setData(newData);
    }

    function updateAppTemplate(updatedAppTemplate) {
        const foundIndex = data.appTemplates.findIndex(appTemplate => appTemplate.id === updatedAppTemplate.id);
        const newData = { ...data };

        if (foundIndex > -1) {
            newData.appTemplates[foundIndex] = updatedAppTemplate
        } else {
            data.appTemplates.push(updatedAppTemplate);
        }

        setData(newData);
        // TODO: Add send to server
        setCurrentAppTemplate(null)

    }

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("appTemplateOverview.title")}
            </Typography>
            <Grid container spacing={2}>
                {data.appTemplates.map(appTemplate => (
                    <Grid item key={appTemplate.id} sm={12}>
                        <TemplateCard
                            appTemplate={appTemplate} handleEdit={setCurrentAppTemplate} handleDelete={deleteAppTemplate} />
                    </Grid>
                ))}
            </Grid>
            <AppTemplateDialog
                appTemplate={currentAppTemplate}
                onClose={() => setCurrentAppTemplate(null)}
                handleSave={updateAppTemplate}
                appTemplates={data.appTemplates}
            />
        </Container>
    )
}

export default AppTemplateOverview