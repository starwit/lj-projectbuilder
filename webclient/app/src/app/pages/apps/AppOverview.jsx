import React, {useCallback, useEffect, useMemo, useState} from "react";
import {Container, Fab, Grid, Typography} from "@mui/material";
import AppCard from "../../features/apps/appCard/AppCard";
import {useHistory} from "react-router-dom";
import {useTranslation} from "react-i18next";
import ApplicationRest from "../../services/ApplicationRest";
import LoadingSpinner from "../../commons/loadingSpinner/LoadingSpinner";
import Statement from "../../commons/statement/Statement";
import {Add, Clear} from "@mui/icons-material";
import AppOverviewStyles from "./AppOverviewStyles";
import {convert} from "generator-jhipster/jdl/converters/jdl-to-json/jdl-without-application-to-json-converter";

function AppOverview() {
    const history = useHistory();
    const {t} = useTranslation();
    const applicationRest = useMemo(() => new ApplicationRest(), []);
    const appOverviewStyles = AppOverviewStyles();

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

    useEffect(() => {
        convert(
            "application {\n" +
            "  config {\n" +
            "    baseName blog\n" +
            "    applicationType monolith\n" +
            "    packageName com.jhipster.demo.blog\n" +
            "    buildTool maven,\n" +
            "    testFrameworks [cypress]\n" +
            "  }\n" +
            "  entities *\n" +
            "}\n" +
            "\n" +
            "entity Blog {\n" +
            "  name String required minlength(3)\n" +
            "  handle String required minlength(2)\n" +
            "}\n" +
            "\n" +
            "entity Post {\n" +
            "  title String required\n" +
            "  content TextBlob required\n" +
            "  date Instant required\n" +
            "}\n" +
            "\n" +
            "entity Tag {\n" +
            "  name String required minlength(2)\n" +
            "}\n" +
            "\n" +
            "relationship ManyToOne {\n" +
            "  Blog{user(login)} to User\n" +
            "  Post{blog(name)} to Blog\n" +
            "}\n" +
            "\n" +
            "relationship ManyToMany {\n" +
            "  Post{tag(name)} to Tag{entry}\n" +
            "}\n" +
            "\n" +
            "paginate Post, Tag with infinite-scroll"
        );
    }, []);

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
            <div className={appOverviewStyles.addFab}>
                <Fab color="primary" aria-label="add" onClick={() => history.push("/apps/create/edit")}>
                    <Add/>
                </Fab>
            </div>
        </Container>
    );
}

export default AppOverview;
