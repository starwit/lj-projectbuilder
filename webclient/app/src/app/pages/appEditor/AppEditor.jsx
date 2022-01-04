import React, {useEffect, useState} from "react";
import {Box, Button, Step, StepLabel, Stepper} from "@mui/material";
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
import LoadingButton from "../../commons/loadingButton/LoadingButton";


function AppEditor() {

    const [activeStep, setActiveStep] = useState(0);
    const [selectedTemplate, setSelectedTemplate] = useState(null);
    const appEditorStyles = AppEditorStyles();
    const {t} = useTranslation();
    const history = useHistory();
    const appRest = new ApplicationRest();

    const [appName, setAppName] = useState("");
    const [generalSectionHasFormError, setGeneralSectionHasFormError] = useState(false)
    const [packageName, setPackageName] = useState("");
    const [entities, setEntities] = useState([]);
    const [isNewApp, setIsNewApp] = useState(false);
    const [saveData, setSaveData] = useState(null);
    let {appId} = useParams();

    useEffect(() => {

        if (appId === "create") {
            setIsNewApp(true);
            return;
        }


        // TODO Load app data here
    }, [appId])

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
                    appName={appName}
                    packageName={packageName}
                    templateName={selectedTemplate?.name}
                    entities={entities}
                />
            ),
            condition: true
        },
    ];

    function handleBack() {
        setActiveStep((activeStep - 1))
    }

    function handleNext() {
        setActiveStep((activeStep + 1))
    }

    function isLastStep() {
        return activeStep === steps.length - 1;
    }

    function handleSave() {
        //TODO send to server
        let restRequest;
        setSaveData("loading");
        if (isNewApp) {
            restRequest = appRest.create({
                baseName: appName,
                packageName: packageName,
                template: selectedTemplate,
                entities: entities,
            })
        }

        restRequest.then(response => {
            history.push("/app/" + appId)
        }).catch(response => setSaveData(response.data));
    }

    function renderNextButton() {
        let content = (
            <Button onClick={handleNext} disabled={!steps[activeStep].condition} startIcon={<ChevronRight/>}>
                {t("button.next")}
            </Button>
        )
        if (isLastStep()) {
            content = (
                <LoadingButton
                    onClick={handleSave}
                    disabled={!steps[activeStep].condition}
                    loading={saveData === "loading"}
                    startIcon={<Done/>}
                >
                    {t("button.save")}
                </LoadingButton>
            )
        }
        return content;
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
                <Button
                    color="inherit"
                    disabled={activeStep === 0}
                    onClick={handleBack}
                    className={appEditorStyles.navigationButtonBack}
                    startIcon={<ChevronLeft/>}
                >
                    {t("button.back")}
                </Button>
                <Box className={appEditorStyles.navigationButtonNext}/>
                {renderNextButton()}
            </Box>
            {steps[activeStep].component}
        </div>
    )

}


export default AppEditor;
