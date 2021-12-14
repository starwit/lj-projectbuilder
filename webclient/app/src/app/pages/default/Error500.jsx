import React from "react";
import { Container,Typography } from "@mui/material";
import {useTranslation} from "react-i18next";

function Error500() {
    const {t} = useTranslation();

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("default.error.title")}
            </Typography>
            <div>
            {t("default.error.500")}
            </div>
        </Container>
    )

}

export default Error500;