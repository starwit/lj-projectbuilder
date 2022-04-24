import React, {useCallback, useEffect, useMemo, useState} from "react";
import AppConclusion from "../../features/apps/appSteps/AppConclusion";
import ApplicationRest from "../../services/ApplicationRest";
import {useParams} from "react-router-dom";
import LoadingSpinner from "../../commons/loadingSpinner/LoadingSpinner";
import {useTranslation} from "react-i18next";
import Statement from "../../commons/statement/Statement";
import {Clear} from "@mui/icons-material";
import {newApp, updateApp} from "../../model/App";
import {useImmer} from "use-immer";

function AppSummary(props) {
    const appRest = useMemo(() => new ApplicationRest(), []);
    const {t} = useTranslation();

    const [app, setApp] = useImmer(newApp);
    const [appError, setAppError] = useState(null);
    const {appId} = useParams();

    const loadApp = useCallback(appId => {
        setAppError(null);
        appRest.findById(appId).then(appResponse => {
            setApp(updateApp(app, appResponse.data));
        }).catch(appResponseError => {
            setAppError(appResponseError.response);
        });
    }, [appRest, setApp, setAppError]);

    useEffect(() => {
        loadApp(appId);
    }, [loadApp, appId]);

    if (!app.id && !appError) {
        return <LoadingSpinner
            message={t("app.loading")}
        />;
    }

    if (appError) {
        return <Statement
            message={t("app.LoadingError")}
            icon={<Clear/>}
            actionMessage={t("button.retry")}
            onActionClick={() => loadApp(appId)}
        />;
    }

    return (<>
        <AppConclusion
            app={app}
        />
    </>);
}

export default AppSummary;
