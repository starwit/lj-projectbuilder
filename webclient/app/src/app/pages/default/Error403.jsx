import React from "react";
import { Container,Typography } from "@mui/material";
import {useTranslation} from "react-i18next";

function Error403() {
    const {t} = useTranslation();

    return (
        <Container>
            <Typography variant={"h2"} gutterBottom>
                {t("default.error.403.title")}
            </Typography>
            <div>
            {t("default.error.403")}
            </div>
        </Container>
    )

}

export default Error403;