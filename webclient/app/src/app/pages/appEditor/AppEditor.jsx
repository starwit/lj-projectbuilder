import React, {useEffect, useMemo, useState} from "react";
import {Alert, Box, Snackbar, Step, StepLabel, Stepper} from "@mui/material";
import TemplateSelection from "./sections/templateSection/TemplateSection";
import ErDesigner from "./sections/erSection/ErSection";
import {ChevronLeft, ChevronRight, Done} from "@mui/icons-material";
import AppEditorStyles from "./AppEditorStyles";
import {useTranslation} from "react-i18next";
import ConclusionSection from "./sections/conclusion/ConclusionSection";
import GeneralSection from "./sections/generalSection/GeneralSection";
import {useHistory, useParams} from "react-router-dom";
import RegexConfig from "../../../regexConfig";
import ApplicationRest from "../../services/ApplicationRest";
import {LoadingButton} from "@mui/lab";
import LoadingSpinner from "../../commons/loadingSpinner/LoadingSpinner";



function AppEditor() {

    const [activeStep, setActiveStep] = useState(0);
    const [selectedTemplate, setSelectedTemplate] = useState(null);
    const appEditorStyles = AppEditorStyles();
    const {t} = useTranslation();
    const history = useHistory();
    const appRest = useMemo(() => new ApplicationRest(), []);

    const [isAppLoading, setIsAppLoading] = useState(false);
    const [appName, setAppName] = useState("");
    const [generalSectionHasFormError, setGeneralSectionHasFormError] = useState(false)
    const [packageName, setPackageName] = useState("");
    const [entities, setEntities] = useState([]);
    const [isNewApp, setIsNewApp] = useState(false);
    const [saveError, setSaveError] = useState(null);
    const [isSaving, setIsSaving] = useState(false);
    let {appId} = useParams();

    useEffect(() => {
        setIsAppLoading(true);
        if (appId === "create") {
            setIsNewApp(true);
            setIsAppLoading(false);

        } else {
            appRest.findById(appId).then(response => {
                const {baseName, packageName, template, entities} = response.data;
                setAppName(baseName);
                setPackageName(packageName);
                setSelectedTemplate(template);
                setEntities(entities);
                setIsAppLoading(false);
                setIsNewApp(false);
            })
        }
    }, [appId, appRest])

    useEffect(() => {

        setGeneralSectionHasFormError(!RegexConfig.applicationBaseName.test(appName) || !RegexConfig.packageName.test(packageName))

    }, [packageName, appName])

    const steps = [
        {
            label: t("appEditor.section.general.title"),
            component: <GeneralSection
                packageName={packageName}
                appName={appName}
                setAppName={setAppName}
                setPackageName={setPackageName}
            />,
            condition: appName !== "" && packageName !== "" && !generalSectionHasFormError
        },
        {
            label: t("appEditor.section.template.title"),
            component: <TemplateSelection
                onChange={setSelectedTemplate}
                value={selectedTemplate}/>,
            condition: selectedTemplate
        },
        {
            label: t("appEditor.section.erDesigner.title"),
            component: <ErDesigner
                entities={entities}
                handleUpdateEntities={updatedEntities => setEntities(updatedEntities)}
            />,
            condition: entities.length >= 1
        },
        {
            label: t("appEditor.section.conclusion.title"),
            component: (
                <ConclusionSection
                    appId={+appId}
                    entities={entities}
                    templateName={selectedTemplate?.templateName}
                    credentialsRequired={selectedTemplate?.credentialsRequired}
                    appName={appName}
                    packageName={packageName}
                />
            ),
            condition: true
        },
    ];

    function handleBack() {
        setIsSaving(true);
        handleSave().then(() => {
            setActiveStep((activeStep - 1));
            setIsSaving(false);
        });
    }

    function handleNext() {
        setIsSaving(true);
        handleSave()
            .then(() => {
                setActiveStep((activeStep + 1));
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

        console.log(entitiesEdited)

        const appPackage = {
            id: (isNewApp ? null : appId),
            baseName: appName,
            packageName: packageName,
            template: selectedTemplate,
            entities: entitiesEdited,
        }

        if (isNewApp) {
            restRequest = appRest.create(appPackage)
                .then(response => {
                    const {baseName, packageName, template, entities, id} = response.data;
                    setAppName(baseName);
                    setPackageName(packageName);
                    setSelectedTemplate(template);
                    setEntities(entities);
                    history.push(`/app/${id}/edit`);
                    return response;
                })
                .catch(response => setSaveError(response.data))
        } else {
            restRequest = appRest.update(appPackage)
                .then(response => {
                    const {baseName, packageName, template, entities} = response.data;
                    setAppName(baseName);
                    setPackageName(packageName);
                    setSelectedTemplate(template);
                    setEntities(entities);
                    return response;
                })
                .catch(response => setSaveError(response.data))
        }

        return restRequest;
    }

    function handleFinishButton() {

        const restRequest = handleSave();
        restRequest
            .then(response => {
                console.log("reachedTHen", response)
                history.replace("/app/" + response.data.id)
            })
            .catch(response => setSaveError(response.data));
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
        )
        if (isLastStep()) {
            content = (
                <LoadingButton
                    onClick={handleFinishButton}
                    disabled={!steps[activeStep].condition}
                    loading={isSaving}
                    startIcon={<Done/>}
                >
                    {t("button.save")}
                </LoadingButton>
            )
        }
        return content;
    }

    if (isAppLoading) {
        return <LoadingSpinner message={t("appEditor.loading")}/>;
    }

    return (
        <div className={appEditorStyles.root}>
            <Stepper activeStep={activeStep}>
                {steps.map((step, index) => {
                    const stepProps = {};
                    const labelProps = {};
                    return (
                        <Step key={step.id} {...stepProps}>
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
            <Snackbar open={!!saveError} autoHideDuration={6000} onClose={() => setSaveError(null)}>
                <Alert onClose={() => setSaveError(null)} severity="error" sx={{width: '30%'}}>
                    This is a success message!
                </Alert>
            </Snackbar>
        </div>
    )

}


export default AppEditor;
