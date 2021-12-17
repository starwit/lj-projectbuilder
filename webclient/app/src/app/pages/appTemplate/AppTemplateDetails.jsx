import React from "react";
import { Container,Typography } from "@mui/material";
import {useTranslation} from "react-i18next";

function AppTemplateDetails(props) {
    const {t} = useTranslation();

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("appTemplateEditor.title")}
            </Typography>
        </Container>
    )
}

export default AppTemplateDetails