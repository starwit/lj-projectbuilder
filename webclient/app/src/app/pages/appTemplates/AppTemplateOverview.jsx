import {Container, Grid, Typography} from "@mui/material";
import React, {useEffect, useMemo, useState} from "react";
import {useTranslation} from "react-i18next";
import AppTemplateCard from "../../features/appTemplates/appTemplateCard/AppTemplateCard";
import AppTemplateDialog from "../../features/appTemplates/appTemplateDialog/AppTemplateDialog";
import AppTemplateRest from "../../services/AppTemplateRest";
import UserRest from "../../services/UserRest";
import AppTemplateOverviewStyles from "./AppTempateOverviewStyles";
import {Add} from "@mui/icons-material";
import LoadingSpinner from "../../commons/loadingSpinner/LoadingSpinner";
import Statement from "../../commons/statement/Statement";
import AddFabButton from "../../commons/addFabButton/AddFabButton";

function AppTemplateOverview() {
    const {t} = useTranslation();
    const [openDialog, setOpenDialog] = React.useState(false);
    const [selectedAppTemplate, setSelectedAppTemplate] = useState(false);
    const [appTemplates, setAppTemplates] = useState(null);
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
            setAppTemplates(response.data);
        });
    };

    useEffect(() => {
        userRest.getUserGroups().then(response => {
            setUserGroups(response.data);
        });
    }, [userRest]);

    useEffect(() => {
        reload();
    }, []);

    function renderContent() {
        if (!appTemplates) {
            return (
                <LoadingSpinner message={t("apptemplates.loading")}/>
            );
        }

        if (appTemplates.length === 0) {
            return (
                <Statement
                    icon={<Add/>}
                    message={t("apptemplates.empty")}
                />
            );
        }

        return (
            <Grid container spacing={2}>
                {appTemplates.map(appTemplate => (
                    <Grid item key={appTemplate.id} sm={12} xs={12}>
                        <AppTemplateCard appTemplate={appTemplate} handleRefresh={reload}
                            userGroups={userGroups}/>
                    </Grid>
                ))}
            </Grid>

        );
    }

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("apptemplates.title")}
            </Typography>
            {renderContent()}
            <AppTemplateDialog
                appTemplate={selectedAppTemplate}
                open={openDialog}
                onClose={handleDialogClose}
                onRefresh={reload}
                isCreateDialog={true}
                userGroups={userGroups}
            />
            <AddFabButton onClick={handleDialogOpen}/>
        </Container>
    );
}

export default AppTemplateOverview;
