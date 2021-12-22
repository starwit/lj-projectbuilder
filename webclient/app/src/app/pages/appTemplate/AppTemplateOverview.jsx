import { Container, Grid, Typography, Button } from "@mui/material";
import React, { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import AppTemplateCard from "../../commons/appTemplateCard/AppTemplateCard";
import AppTemplateDialog from "../../commons/appTemplateDialog/AppTemplateDialog";
import AppTemplateRest from "../../services/AppTemplateRest";

function AppTemplateOverview() {
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
        "location": "",
        "branch": "",
        "credentialsRequired": false,
        "description": ""
    };

    const [data, setData] = useState([]);

    const reload = () => {
        const appTemplateRest = new AppTemplateRest();
        appTemplateRest.findAll().then(response => {
            setData(response.data);
        });
    };

    useEffect(() => {
        reload();
    },[]);

    return (
        <Container >
            <Grid container spacing={2}>
                <Grid item xs={9}>
                    <Typography variant={"h2"} gutterBottom>
                        {t("appTemplateOverview.title")}
                    </Typography>
                </Grid>
                <Grid item xs={3} align="right" >
                    <br />
                    <Button variant="contained" onClick={handleDialogOpen} >{t("button.create")}</Button>
                </Grid>
            </Grid>
                <Grid container spacing={2}>
                    {data.map(appTemplate => (
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