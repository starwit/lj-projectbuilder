import React from "react";
import {Container, Grid} from "@mui/material";
import Image from "../../../../assets/images/logo.png"
import AppTemplateSelectCard from "../../../../commons/appTemplateSelectCard/AppTemplateSelectCard";


function TemplateSection(props) {

    const {onChange, value} = props;

    const templates = [
        {
            id: 1,
            name: "Standard Runtime",
            description: "Das ist eine lange Beschreibung der Standard-Runtime",
            image: Image
        },
        {
            id: 2,
            name: "Andere Runtime",
            description: "Das ist eine lange Beschreibung der Standard-Runtime",
            image: Image
        },
    ]

    function handleSelection(template) {
        onChange(template)
    }

    return (
        <Container>
            <Grid container spacing={5}>
                {templates.map(template => (
                    <Grid item sm={3}>
                        <AppTemplateSelectCard
                            template={template}
                            onSelection={handleSelection}
                            selected={value?.id === template.id}
                        />
                    </Grid>
                ))}

            </Grid>
        </Container>
    )

}

TemplateSection.defaultProps = {
    onChange: () => {
        //This is intentional
    }
}

export default TemplateSection;