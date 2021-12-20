import React, {useEffect, useState} from "react";
import {Box, Button, Step, StepLabel, Stepper} from "@mui/material";
import TemplateSelection from "./sections/templateSection/TemplateSection";
import ErDesigner from "./sections/erSection/ErSection";
import {ChevronLeft, ChevronRight, Done} from "@mui/icons-material";
import AppEditorStyles from "./AppEditorStyles";
import {useTranslation} from "react-i18next";
import ConclusionSection from "./sections/conclusion/ConclusionSection";


function AppEditor() {

    const [activeStep, setActiveStep] = useState(1);
    const [selectedTemplate, setSelectedTemplate] = useState(null)
    const appEditorStyles = AppEditorStyles();
    const {t} = useTranslation();
    const [appName, setAppName] = useState(null)
    const [packageName, setPackageName] = useState(null)
    // This is temporary. setAppName and setPackageName will be used later when everything is being wired together
    useEffect(() => {
        setAppName(null);
        setPackageName(null);
    }, [])
    const [entities, setEntities] = useState([
        {
            "id": 1,
            "name": "D1",
            "fields": [
                {
                    "name": "D1-f1-s",
                    "description": "",
                    "dataType": {
                        "id": 1,
                        "name": "string"
                    },
                    "pattern": "",
                    "min": "",
                    "max": "",
                    "mandatory": false
                },
                {
                    "name": "D1-f1-i",
                    "description": "",
                    "dataType": {
                        "id": 2,
                        "name": "integer",
                        "allowMin": true,
                        "allowMax": true
                    },
                    "pattern": "",
                    "min": "",
                    "max": "",
                    "mandatory": false
                }
            ],
            "relationships": []
        },
        {
            "id": 2,
            "name": "D2",
            "fields": [
                {
                    "name": "D2-f1-i",
                    "description": "",
                    "dataType": {
                        "id": 2,
                        "name": "integer",
                        "allowMin": true,
                        "allowMax": true
                    },
                    "pattern": "",
                    "min": "",
                    "max": "",
                    "mandatory": false
                },
                {
                    "name": "D2-f2-s",
                    "description": "",
                    "dataType": {
                        "id": 1,
                        "name": "string"
                    },
                    "pattern": "",
                    "min": "",
                    "max": "",
                    "mandatory": false
                }
            ],
            "relationships": [
                {
                    "relationshipType": "one-to-many",
                    "otherEntityName": "D1",
                    "otherEntityRelationshipName": "D1-f1-i",
                    "relationshipName": "D2-f1-i",
                    "name": "D2"
                }
            ]
        }
    ])

    const steps = [
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
            condition: entities.length > 1
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

    function renderNextButton() {
        let content = (
            <Button onClick={handleNext} disabled={!steps[activeStep].condition} startIcon={<ChevronRight/>}>
                {t("button.next")}
            </Button>
        )
        if (isLastStep()) {
            content = (
                <Button onClick={handleNext} disabled={!steps[activeStep].condition} startIcon={<Done/>}>
                    {t("button.save")}
                </Button>
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