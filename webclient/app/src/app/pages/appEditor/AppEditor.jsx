import React, {useState} from "react";
import {Box, Button, Step, StepLabel, Stepper} from "@mui/material";
import AddCard from "../../commons/addCard/AddCard";
import TemplateSection from "./sections/templateSection/TemplateSection";
import ErSection from "./sections/erSection/ErSection";
import {ChevronLeft, ChevronRight, Done} from "@mui/icons-material";
import AppEditorStyles from "./AppEditorStyles";
import {useTranslation} from "react-i18next";


function AppEditor() {


    const [activeStep, setActiveStep] = useState(1);
    const [selectedTemplate, setSelectedTemplate] = useState(null)
    const appEditorStyles = AppEditorStyles();
    const {t} = useTranslation();

    const steps = [
        {
            label: t("appEditor.section.template.title"),
            component: <TemplateSection onChange={setSelectedTemplate} value={selectedTemplate}/>,
            condition: selectedTemplate
        },
        {
            label: t("appEditor.section.erDesigner.title"),
            component: <ErSection/>
        },
        {
            label: t("appEditor.section.conclusion.title"),
            component: <AddCard/>
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