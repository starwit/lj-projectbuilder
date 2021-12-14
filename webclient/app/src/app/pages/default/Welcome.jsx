import React from "react";
import { Container,Typography } from "@mui/material";
import {useTranslation} from "react-i18next";

function Welcome() {
    const {t} = useTranslation();

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("default.welcome")}
            </Typography>
        </Container>
    )

}

export default Welcome;