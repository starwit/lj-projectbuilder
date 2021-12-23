import React, {useCallback, useEffect, useMemo, useState} from "react";
import {Container, Grid, Typography} from "@mui/material";
import AppCard from "../../commons/appCard/AppCard";
import {useHistory} from "react-router-dom";
import {useTranslation} from "react-i18next";
import ApplicationRest from "../../services/ApplicationRest";
import LoadingSpinner from "../../commons/loadingSpinner/LoadingSpinner";
import AddCard from "../../commons/addCard/AddCard";
import Statement from "../../commons/statement/Statement";
import {Clear} from "@mui/icons-material";

function Home() {
    const history = useHistory();
    const {t} = useTranslation();
    const applicationRest = useMemo(() => new ApplicationRest(), []);

    const [apps, setApps] = useState(null);
    const [appsError, setAppsError] = useState(null);

    const loadApps = useCallback(() => {
        setApps(null);
        setAppsError(null);
        applicationRest.getAll().then(allAppsResponse => {
            setApps(allAppsResponse.data);
        }).catch(allAppsResponseError => {
            setAppsError(allAppsResponseError.response)
        })
    }, [applicationRest, setApps, setAppsError])

    useEffect(() => {
        loadApps();
    }, [loadApps])

    function renderApps() {
        if (!apps) {
            return <LoadingSpinner message={t("home.loading")}/>;
        }

        if (appsError) {
            return <Statement
                message={t("home.loadingError")}
                icon={<Clear/>}
                actionMessage={t("button.retry")}
                onActionClick={loadApps}
            />;
        }

        return (
            <Grid container spacing={5}>
                {apps.map(app => (
                    <Grid item sm={4}>
                        <AppCard
                            onEditClick={() => {
                                history.push("/app/" + app.id + "/edit")
                            }}
                            onDeleteClick={() => {
                            }}
                            app={app}/>
                    </Grid>
                ))}
                <Grid item sm={4}>
                    <AddCard
                        onClick={() => {
                        }}
                    />
                </Grid>
            </Grid>
        )
    }

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("home.yourApps")}
            </Typography>
            {renderApps()}


        </Container>
    )

}

export default Home;