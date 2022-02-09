import { Container, Grid, Typography, Fab } from "@mui/material";
import React, { useState, useMemo, useEffect } from "react";
import { useTranslation } from "react-i18next";
import AppTemplateCard from "../../features/appTemplates/appTemplateCard/AppTemplateCard";
import AppTemplateDialog from "../../features/appTemplates/appTemplateDialog/AppTemplateDialog";
import AppTemplateRest from "../../services/AppTemplateRest";
import UserRest from "../../services/UserRest";
import AppTemplateOverviewStyles from "./AppTempateOverviewStyles";
import { Add } from "@mui/icons-material";

function AppTemplateOverview() {
    const { t } = useTranslation();
    const [openDialog, setOpenDialog] = React.useState(false);
    const [selectedAppTemplate, setSelectedAppTemplate] = useState(false);
    const [data, setData] = useState([]);
    const [userGroups, setUserGroups] = React.useState([]); 
    const userRest = useMemo(() => new UserRest(), []);
    const appTemplateOverviewStyles = AppTemplateOverviewStyles();    

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
        "description": "",
        "groups": ["public"]
    };

    const reload = () => {
        const appTemplateRest = new AppTemplateRest();
        appTemplateRest.findAll().then(response => {
            setData(response.data);
        });
    };

    useEffect(() => {
        userRest.getUserGroups().then((response) => {
            setUserGroups(response.data);
        });
    }, [userRest]);

    useEffect(() => {
        reload();
    },[]);

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("apptemplates.title")}
            </Typography>
            <Grid container spacing={2}>
                {data.map(appTemplate => (
                    <Grid item key={appTemplate.id} sm={12} xs={12}>
                        <AppTemplateCard appTemplate={appTemplate} handleRefresh={reload} userGroups={userGroups} />
                    </Grid>
                ))}
            </Grid>
            <AppTemplateDialog
                appTemplate={selectedAppTemplate}
                open={openDialog}
                onClose={handleDialogClose}
                onRefresh={reload}
                isCreateDialog={true}
                userGroups={userGroups}
            />
            <div className={appTemplateOverviewStyles.addFab}>
                <Fab color="primary" aria-label="add" onClick={handleDialogOpen}>
                    <Add/>
                </Fab>
            </div>
        </Container>
    )
}

export default AppTemplateOverview