import React, {useState} from "react";
import {Box, Button, Container, Step, StepLabel, Stepper} from "@mui/material";
import AddCard from "../../commons/addCard/AddCard";
import TemplateSelection from "./sections/templateSelection/TemplateSelection";
import ErDesigner from "./sections/erDesigner/ErDesigner";


function ProjectEditor() {


    const [activeStep, setActiveStep] = useState(0);
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

    return (
        <div style={{paddingTop: "2rem"}}>
            <Stepper activeStep={activeStep} style={{paddingBottom: "2rem"}}>
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
            {steps[activeStep].component}
            <Box sx={{display: 'flex', flexDirection: 'row', pt: 2}}>
                <Button
                    color="inherit"
                    disabled={activeStep === 0}
                    onClick={handleBack}
                    sx={{mr: 1}}
                >
                    Zurück
                </Button>
                <Box sx={{flex: '1 1 auto'}}/>

                <Button onClick={handleNext} disabled={!steps[activeStep].condition}>
                    {activeStep === steps.length - 1 ? 'Abschließen' : 'Weiter'}
                </Button>
            </Box>
        </div>
    )

}

export default ProjectEditor;