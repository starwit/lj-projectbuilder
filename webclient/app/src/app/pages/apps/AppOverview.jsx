import React, {useCallback, useEffect, useMemo, useState} from "react";
import {Container, Grid, Typography} from "@mui/material";
import AppCard from "../../features/apps/appCard/AppCard";
import {useHistory} from "react-router-dom";
import {useTranslation} from "react-i18next";
import ApplicationRest from "../../services/ApplicationRest";
import Statement from "../../commons/statement/Statement";
import {Add, Clear} from "@mui/icons-material";
import AddFabButton from "../../commons/buttons/addFabButton/AddFabButton";
import {LoadingSpinner} from "@starwit/frontend-commons";

function AppOverview() {
    const history = useHistory();
    const {t} = useTranslation();
    const applicationRest = useMemo(() => new ApplicationRest(), []);

    const [apps, setApps] = useState(null);
    const [appsError, setAppsError] = useState(null);

    const loadApps = useCallback(() => {
        setApps(null);
        setAppsError(null);
        applicationRest.findAll().then(allAppsResponse => {
            setApps(allAppsResponse.data);
        }).catch(allAppsResponseError => {
            setAppsError(allAppsResponseError.response);
        });
    }, [applicationRest, setApps, setAppsError]);

    useEffect(() => {
        loadApps();
    }, [loadApps]);

    function deleteById(id) {
        return applicationRest.delete(id)
            .then(response => {
                setApps(null);
                loadApps();
            });
    }

    function renderApps() {
        if (!apps) {
            return <LoadingSpinner message={t("apps.loading")}/>;
        }

        if (appsError) {
            return <Statement
                message={t("apps.loadingError")}
                icon={<Clear/>}
                actionMessage={t("button.retry")}
                onActionClick={loadApps}
            />;
        }

        if (apps.length === 0) {
            return (
                <Statement
                    icon={<Add/>}
                    message={t("apps.empty")}
                />
            );
        }

        return (
            <Grid container spacing={5}>
                {apps.map(app => (
                    <Grid item sm={6} xs={12} key={app.id}>
                        <AppCard
                            onEditClick={() => {
                                history.push("/apps/" + app.id + "/edit");
                            }}
                            onDeleteClick={deleteById}
                            app={app}/>
                    </Grid>
                ))}
            </Grid>
        );
    }

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("apps.title")}
            </Typography>
            {renderApps()}
            <AddFabButton onClick={() => history.push("/apps/create/edit")}/>
        </Container>
    );
}

export default AppOverview;
