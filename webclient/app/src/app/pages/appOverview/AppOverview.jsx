import React, {useCallback, useEffect, useMemo, useState} from "react";
import ConclusionSection from "../appEditor/sections/conclusion/ConclusionSection";
import ApplicationRest from "../../services/ApplicationRest";
import {useParams} from "react-router-dom";
import LoadingSpinner from "../../commons/loadingSpinner/LoadingSpinner";
import {useTranslation} from "react-i18next";
import Statement from "../../commons/statement/Statement";
import {Clear} from "@mui/icons-material";

function AppOverview(props) {

    const applicationRest = useMemo(() => new ApplicationRest(), []);
    const {t} = useTranslation();

    const [app, setApp] = useState(null);
    const [appError, setAppError] = useState(null);
    let {appId} = useParams();


    const loadApp = useCallback((appId) => {
        setApp(null);
        setAppError(null);
        applicationRest.findById(appId).then(appResponse => {
            setApp(appResponse.data);
        }).catch(appResponseError => {
            setAppError(appResponseError.response)
        })
    }, [applicationRest, setApp, setAppError])

    useEffect(() => {
        loadApp(appId);
    }, [loadApp, appId])


    if (!app && !appError) {
        return <LoadingSpinner
            message={t("appOverview.app.isLoading")}
        />
    }

    if (appError) {
        return <Statement
            message={t("appOverview.app.error")}
            icon={<Clear/>}
            actionMessage={t("button.retry")}
            onActionClick={() => loadApp(appId)}
        />;
    }

    return (<>
        <ConclusionSection
            appName={app.baseName}
            entities={app.entities}
            packageName={app.packageName}
            templateName={app.template.name}
        />
    </>);
}

export default AppOverview