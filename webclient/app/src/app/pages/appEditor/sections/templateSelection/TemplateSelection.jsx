import React from "react";
import {Container, Grid} from "@mui/material";
import TemplateCard from "../../../../commons/templateCard/TemplateCard";
import Image from "../../../../assets/images/logo.png"


function TemplateSelection(props) {

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
                        <TemplateCard template={template} onSelection={handleSelection}
                                      selected={value?.id === template.id}/>
                    </Grid>
                ))}

            </Grid>
        </Container>
    )

}

TemplateSelection.defaultProps = {
    onChange: () => {
        //This is intentional
    }
}

export default TemplateSelection;