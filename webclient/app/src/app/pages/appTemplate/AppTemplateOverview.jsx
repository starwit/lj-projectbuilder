import { Container, Grid, Typography, Button } from "@mui/material";
import React, { useState } from "react";
import { useTranslation } from "react-i18next";
import AppTemplateCard from "../../commons/appTemplateCard/AppTemplateCard";
import AppTemplateDialog from "../../commons/appTemplateDialog/AppTemplateDialog";

function AppTemplateOverview(props) {
    const { t } = useTranslation();
    const [openDialog, setOpenDialog] = React.useState(false);
    const [selectedAppTemplate, setSelectedAppTemplate] = useState(false);

    const handleDialogOpen = () => {
        setOpenDialog(true);
        setSelectedAppTemplate(defaultAppTemplate);
    };

    const handleDialogClose = () => {
        setOpenDialog(false);
    };

    const defaultAppTemplate =
    {
        "id": "-1",
        "location": "",
        "branch": "",
        "credentialsRequired": false,
        "description": ""
    };
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

    function reload() {
        const newData = { ...data };
        // TODO: load apTemplates from server
        setData(newData);
    }

    return (
        <Container>
            <Grid container spacing={2}>
                <Grid item xs={9}>
                    <Typography variant={"h2"} gutterBottom>
                        {t("appTemplateOverview.title")}
                    </Typography>
                </Grid>
                <Grid item xs={3} align="right">
                    <Button onClick={handleDialogOpen} >{t("button.create")}</Button>
                </Grid>
            </Grid>

            <Grid container spacing={2}>
                {data.appTemplates.map(appTemplate => (
                    <Grid item key={appTemplate.id} sm={12}>
                        <AppTemplateCard appTemplate={appTemplate} handleRefresh={reload} />
                    </Grid>
                ))}
            </Grid>
            <AppTemplateDialog
                appTemplate={selectedAppTemplate}
                open={openDialog}
                onClose={handleDialogClose}
                onRefresh={reload}
                isCreateDialog={true}
            />
        </Container>
    )
}

export default AppTemplateOverview