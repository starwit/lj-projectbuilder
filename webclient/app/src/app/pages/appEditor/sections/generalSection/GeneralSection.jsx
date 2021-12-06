import React from "react";
import {TextField} from "@mui/material";
import {useTranslation} from "react-i18next";

function GeneralSection() {

    const {t} = useTranslation();

    return (
        <>
            <TextField label={t("generalSection.nameOfApp")} fullWidth></TextField>
            <TextField label={t("generalSection.descriptionOfApp")} fullWidth></TextField>
        </>
    )

}

export default GeneralSection;