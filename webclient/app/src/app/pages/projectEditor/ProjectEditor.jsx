import React, {useState} from "react";
import {Box, Button, Container, Step, StepLabel, Stepper} from "@mui/material";
import AddCard from "../../commons/addCard/AddCard";
import TemplateSelection from "./sections/templateSelection/TemplateSelection";
import ErDesigner from "./sections/erDesigner/ErDesigner";
import {ChevronLeft, ChevronRight, Done} from "@mui/icons-material";


function ProjectEditor() {


    const [activeStep, setActiveStep] = useState(1);
    const [selectedTemplate, setSelectedTemplate] = useState(null)

    const steps = [
        {
            label: "Template",
            component: <TemplateSelection onChange={setSelectedTemplate} value={selectedTemplate}/>,
            condition: selectedTemplate
        },
        {
            label: "ER-Designer",
            component: <ErDesigner/>
        },,
        {
            label: "Abschließen",
            component: <AddCard/>
        },
    ];

    console.log(selectedTemplate)
    function handleBack() {
        setActiveStep((activeStep - 1))
    }

    function handleNext() {
        setActiveStep((activeStep + 1))
    }

    function isLastStep(){
        return activeStep === steps.length - 1;
    }

    function renderNextButton(){
        let content = (
            <Button onClick={handleNext} disabled={!steps[activeStep].condition} startIcon={<ChevronRight/>}>
                Weiter
            </Button>
        )
        if (isLastStep()){
            content = (
                <Button onClick={handleNext} disabled={!steps[activeStep].condition} startIcon={<Done/>} >
                    Abschließen
                </Button>
            )
        }
        return content;
    }

    return (
        <div style={{paddingTop: "2rem"}}>
            <Stepper activeStep={activeStep} >
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
            <Box sx={{display: 'flex', flexDirection: 'row', paddingBottom: "2rem"}}>
                <Button
                    color="inherit"
                    disabled={activeStep === 0}
                    onClick={handleBack}
                    sx={{mr: 1}}
                    startIcon={<ChevronLeft/>}
                >
                    Zurück
                </Button>
                <Box sx={{flex: '1 1 auto'}}/>

                {renderNextButton()}
            </Box>
            {steps[activeStep].component}

        </div>
    )

}

export default ProjectEditor;