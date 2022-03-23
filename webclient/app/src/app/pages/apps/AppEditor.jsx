import React, { useEffect, useMemo, useState } from "react";
import { Box, Step, StepLabel, Stepper } from "@mui/material";
import AppTemplateSelection from "../../features/apps/appSteps/AppTemplateSelection";
import EntityDiagram from "../../features/apps/entities/entityDiagram/EntityDiagram";
import { ChevronLeft, ChevronRight, Done } from "@mui/icons-material";
import AppEditorStyles from "./AppEditorStyles";
import { useTranslation } from "react-i18next";
import AppConclusion from "../../features/apps/appSteps/AppConclusion";
import AppGeneral from "../../features/apps/appSteps/AppGeneral";
import { useHistory, useParams } from "react-router-dom";
import RegexConfig from "../../../regexConfig";
import ApplicationRest from "../../services/ApplicationRest";
import { LoadingButton } from "@mui/lab";
import LoadingSpinner from "../../commons/loadingSpinner/LoadingSpinner";
import { updateRelationCoordinates } from "../../features/apps/entities/HandleRelations";
import UserRest from "../../services/UserRest";

function AppEditor() {
    const [activeStep, setActiveStep] = useState(0);
    const [selectedTemplate, setSelectedTemplate] = useState(null);
    const appEditorStyles = AppEditorStyles();
    const { t } = useTranslation();
    const history = useHistory();
    const appRest = useMemo(() => new ApplicationRest(), []);
    const userRest = useMemo(() => new UserRest(), []);

    const [isAppLoading, setIsAppLoading] = useState(false);
    const [appName, setAppName] = useState("");
    const [appGeneralHasFormError, setAppGeneralHasFormError] = useState(false);
    const [packageName, setPackageName] = useState("");
    const [entities, setEntities] = useState([]);
    const [entityRelationCoordinates, setEntityRelationCoordinates] = useState(
        []
    );
    const [isNewApp, setIsNewApp] = useState(false);
    const [isSaving, setIsSaving] = useState(false);
    const [userGroups, setUserGroups] = useState([]);
    const [groupsToAssign, setGroupsToAssign] = useState(["public"]);
    let { appId } = useParams();

    useEffect(() => {
        setIsAppLoading(true);
        if (appId === "create") {
            setIsNewApp(true);
            setIsAppLoading(false);
        } else {
            appRest.findById(appId).then(response => {
                const {
                    baseName,
                    packageName,
                    template,
                    entities,
                    groupsToAssign,
                } = response.data;
                setAppName(baseName);
                setPackageName(packageName);
                setSelectedTemplate(template);
                handleUpdateEntities(entities);
                setIsAppLoading(false);
                setIsNewApp(false);
                setGroupsToAssign(groupsToAssign);
            });
        }
    }, [appId, appRest]);

    useEffect(() => {
        setAppGeneralHasFormError(
            !RegexConfig.applicationBaseName.test(appName) ||
                !RegexConfig.packageName.test(packageName)
        );
    }, [packageName, appName]);

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
                    isCreate={isNewApp}
                    packageName={packageName}
                    appName={appName}
                    setAppName={setAppName}
                    setPackageName={setPackageName}
                    userGroups={userGroups}
                    assignedGroups={groupsToAssign}
                    setAssignedGroups={setGroupsToAssign}
                />
            ),
            condition:
                appName !== "" && packageName !== "" && !appGeneralHasFormError,
        },
        {
            label: t("app.section.template"),
            component: (
                <AppTemplateSelection
                    onChange={setSelectedTemplate}
                    value={selectedTemplate}
                />
            ),
            condition: selectedTemplate,
        },
        {
            label: t("app.section.entityDiagram"),
            component: (
                <EntityDiagram
                    appId={+appId}
                    entities={entities}
                    coordinates={entityRelationCoordinates}
                    handleUpdateEntities={handleUpdateEntities}
                />
            ),
            condition: entities.length >= 1,
        },
        {
            label: t("app.section.conclusion"),
            component: (
                <AppConclusion
                    appId={+appId}
                    entities={entities}
                    coordinates={updateRelationCoordinates(entities)}
                    templateName={
                        selectedTemplate ? selectedTemplate?.name : null
                    }
                    credentialsRequired={
                        selectedTemplate
                            ? selectedTemplate?.credentialsRequired
                            : null
                    }
                    appName={appName}
                    packageName={packageName}
                />
            ),
            condition: true,
        },
    ];

    function handleUpdateEntities(updatedEntities) {
        setEntities(updatedEntities);
        setEntityRelationCoordinates(
            updateRelationCoordinates(updatedEntities)
        );
    }

    function handleBack() {
        setIsSaving(true);
        handleSave().then(() => {
            setActiveStep(activeStep - 1);
            setIsSaving(false);
        });
    }

    function handleNext() {
        setIsSaving(true);
        handleSave().then(() => {
            setActiveStep(activeStep + 1);
            setIsSaving(false);
        });
    }

    function isLastStep() {
        return activeStep === steps.length - 1;
    }

    function handleSave() {
        let restRequest;
        let entitiesEdited = [...entities].map(entity => {
            entity.id = null;
            return entity;
        });

        const appPackage = {
            id: isNewApp ? null : appId,
            baseName: appName,
            packageName: packageName,
            template: selectedTemplate,
            entities: entitiesEdited,
            groupsToAssign: groupsToAssign,
            userGroups: userGroups,
        };

        if (isNewApp) {
            restRequest = appRest.create(appPackage).then(response => {
                const {
                    baseName,
                    packageName,
                    template,
                    entities,
                    groupsToAssign,
                    id,
                } = response.data;
                setAppName(baseName);
                setPackageName(packageName);
                setSelectedTemplate(template);
                handleUpdateEntities(entities);
                setGroupsToAssign(groupsToAssign);
                history.push(`/apps/${id}/edit`);
                return response;
            });
        } else {
            restRequest = appRest.update(appPackage).then(response => {
                const {
                    baseName,
                    packageName,
                    template,
                    entities,
                    groupsToAssign,
                } = response.data;
                setAppName(baseName);
                setPackageName(packageName);
                setSelectedTemplate(template);
                handleUpdateEntities(entities);
                setGroupsToAssign(groupsToAssign);
                return response;
            });
        }

        return restRequest;
    }

    function handleFinishButton() {
        const restRequest = handleSave();
        restRequest.then(response => {
            history.replace("/");
        });
    }

    function renderNextButton() {
        let content = (
            <LoadingButton
                onClick={handleNext}
                disabled={!steps[activeStep].condition}
                startIcon={<ChevronRight />}
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
                    startIcon={<Done />}
                >
                    {t("button.done")}
                </LoadingButton>
            );
        }
        return content;
    }

    if (isAppLoading) {
        return <LoadingSpinner message={t("app.loading")} />;
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
                    startIcon={<ChevronLeft />}
                    loading={isSaving}
                >
                    {t("button.back")}
                </LoadingButton>
                <Box className={appEditorStyles.navigationButtonNext} />
                {renderNextButton()}
            </Box>
            {steps[activeStep].component}
        </div>
    );
}

export default AppEditor;
