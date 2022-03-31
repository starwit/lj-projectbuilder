import React, {useCallback, useEffect, useMemo, useState} from "react";
import AppConclusion from "../../features/apps/appSteps/AppConclusion";
import ApplicationRest from "../../services/ApplicationRest";
import {useParams} from "react-router-dom";
import LoadingSpinner from "../../commons/loadingSpinner/LoadingSpinner";
import {useTranslation} from "react-i18next";
import Statement from "../../commons/statement/Statement";
import {Clear} from "@mui/icons-material";
import {updateRelationCoordinates} from "../../features/apps/entities/HandleRelations";

function AppOverview(props) {
    const applicationRest = useMemo(() => new ApplicationRest(), []);
    const {t} = useTranslation();

    const [app, setApp] = useState(null);
    const [appError, setAppError] = useState(null);
    const [coordinates, setCoordinates] = useState(null);
    const {appId} = useParams();


    const loadApp = useCallback((appId) => {
        setApp(null);
        setAppError(null);
        applicationRest.findById(appId).then((appResponse) => {
            setApp(appResponse.data);
            setCoordinates(updateRelationCoordinates(appResponse.data.entities));
        }).catch((appResponseError) => {
            setAppError(appResponseError.response);
        });
    }, [applicationRest, setApp, setAppError]);

    useEffect(() => {
        loadApp(appId);
    }, [loadApp, appId]);


    if (!app && !appError) {
        return <LoadingSpinner
            message={t("app.loading")}
        />;
    }

    if (appError) {
        return <Statement
            message={t("app.LoadingError")}
            icon={<Clear />}
            actionMessage={t("button.retry")}
            onActionClick={() => loadApp(appId)}
        />;
    }

    return (<>
        <AppConclusion
            appId={+appId}
            appName={app.baseName}
            entities={app.entities}
            coordinates={coordinates}
            packageName={app.packageName}
            templateName={app.template.name}
            credentialsRequired={app.template.credentialsRequired}
        />
    </>);
}

export default AppOverview;
