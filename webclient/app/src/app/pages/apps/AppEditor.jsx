import React, {useEffect, useMemo, useState} from "react";
import {Box, Step, StepLabel, Stepper} from "@mui/material";
import AppTemplateSelection from "../../features/apps/appSteps/AppTemplateSelection";
import EntityDiagram from "../../features/apps/entities/entityDiagram/EntityDiagram";
import {ChevronLeft, ChevronRight, Done} from "@mui/icons-material";
import AppEditorStyles from "./AppEditorStyles";
import {useTranslation} from "react-i18next";
import AppConclusion from "../../features/apps/appSteps/AppConclusion";
import AppGeneral from "../../features/apps/appSteps/AppGeneral";
import {useHistory, useParams} from "react-router-dom";
import ApplicationRest from "../../services/ApplicationRest";
import {LoadingButton} from "@mui/lab";
import {useImmer} from "use-immer";
import {newApp, toDatabaseApp, updateApp, updateEntities, updateGeneral, updateTemplate} from "../../model/App";
import UserRest from "../../services/UserRest";
import {LoadingSpinner} from "@starwit/react-starwit";

function AppEditor() {
    const [activeStep, setActiveStep] = useState(0);
    const appEditorStyles = AppEditorStyles();
    const {t} = useTranslation();
    const history = useHistory();
    const appRest = useMemo(() => new ApplicationRest(), []);
    const userRest = useMemo(() => new UserRest(), []);
    const [userGroups, setUserGroups] = useState([]);
    const [isAppLoading, setIsAppLoading] = useState(false);
    const [isSaving, setIsSaving] = useState(false);
    const {appId} = useParams();
    const [app, setApp] = useImmer(newApp);

    useEffect(() => {
        setIsAppLoading(true);
        if (appId === "create") {
            setIsAppLoading(false);
        } else {
            appRest.findById(appId).then(response => {
                setApp(updateApp(app, response.data));
                setIsAppLoading(false);
            });
        }
    }, [appId, appRest]);

    useEffect(() => {
        userRest.getUserGroups().then(response => {
            setUserGroups(response.data);
        });
    }, [userRest]);

    const steps = [
        {
            label: t("app.section.general"),
            component: (
                <AppGeneral
                    value={app.general}
                    userGroups={userGroups}
                    onChange={changeEvent => setApp(updateGeneral(app, changeEvent))}
                />
            ),
            condition: app.general.isValid
        },
        {
            label: t("app.section.template"),
            component: (
                <AppTemplateSelection
                    onChange={template => setApp(updateTemplate(app, template))}
                    value={app.template}
                />
            ),
            condition: app.template
        },
        {
            label: t("app.section.entityDiagram"),
            component: (
                <EntityDiagram
                    appId={app.id}
                    entities={app.entities}
                    onChange={entities => setApp(updateEntities(app, entities))}
                />
            ),
            condition: app.entities?.length >= 1
        },
        {
            label: t("app.section.conclusion"),
            component: (
                <AppConclusion
                    app={app}
                />
            ),
            condition: true
        }
    ];

    function handleStep(nextActiveStep) {
        setIsSaving(true);
        handleSave()
            .then(() => {
                setActiveStep(nextActiveStep);
                setIsSaving(false);
            })
            .catch(() => {
                setIsSaving(false);
            });
    }

    function handleBack() {
        return handleStep((activeStep - 1));
    }

    function handleNext() {
        return handleStep((activeStep + 1));
    }

    function isLastStep() {
        return activeStep === steps.length - 1;
    }

    function handleSave() {
        let restRequest;
        if (app.general.isNew) {
            restRequest = appRest.create(toDatabaseApp(app))
                .then(response => {
                    history.push(`/apps/${response.data.id}/edit`);
                    return response;
                });
        } else {
            restRequest = appRest.update(toDatabaseApp(app))
                .then(response => {
                    setApp(updateApp(app, response.data));
                    return response;
                });
        }

        return restRequest;
    }

    function handleFinishButton() {
        const restRequest = handleSave();
        restRequest
            .then(() => {
                history.replace("/");
            });
    }

    function renderNextButton() {
        let content = (
            <LoadingButton
                onClick={handleNext}
                disabled={!steps[activeStep].condition}
                startIcon={<ChevronRight/>}
                loading={isSaving}
            >
                {t("button.next")}
            </LoadingButton>
        );
        if (isLastStep()) {
            content = (
                <LoadingButton
                    onClick={handleFinishButton}
                    disabled={!steps[activeStep].condition}
                    loading={isSaving}
                    startIcon={<Done/>}
                >
                    {t("button.done")}
                </LoadingButton>
            );
        }
        return content;
    }

    if (isAppLoading) {
        return <LoadingSpinner message={t("app.loading")}/>;
    }

    return (
        <div className={appEditorStyles.root}>
            <Stepper activeStep={activeStep}>
                {steps.map((step, index) => {
                    const stepProps = {};
                    const labelProps = {};
                    return (
                        <Step key={index} {...stepProps}>
                            <StepLabel {...labelProps}>{step.label}</StepLabel>
                        </Step>
                    );
                })}
            </Stepper>
            <Box className={appEditorStyles.navigationButtonsArray}>
                <LoadingButton
                    color="inherit"
                    disabled={activeStep === 0}
                    onClick={handleBack}
                    className={appEditorStyles.navigationButtonBack}
                    startIcon={<ChevronLeft/>}
                    loading={isSaving}
                >
                    {t("button.back")}
                </LoadingButton>
                <Box className={appEditorStyles.navigationButtonNext}/>
                {renderNextButton()}
            </Box>
            {steps[activeStep].component}
        </div>
    );
}

export default AppEditor;
