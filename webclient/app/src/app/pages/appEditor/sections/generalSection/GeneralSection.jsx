import React from "react";
import {Container, Grid, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import ValidatedTextField from "../../../../commons/validatedTextField/ValidatedTextField";
import RegexConfig from "../../../../../regexConfig";
import GeneralSectionStyles from "./GeneralSectionStyles";
import MultipleSelectChip from "../../../../commons/appTemplateDialog/ChipSelect";

function GeneralSection(props) {

    const {t} = useTranslation();
    const {setAppName, setPackageName, appName, packageName, userGroups, assignedGroups, setAssignedGroups} = props;
    const generalSectionStyles = GeneralSectionStyles();

    const handleGroupChange = (items) => {
        setAssignedGroups(items);
    };


    return (
        <Container maxWidth={false}>
            <Grid container spacing={5}>
                <Grid item md={8}>
                    <ValidatedTextField
                        label={t("generalSection.nameOfApp")}
                        regex={RegexConfig.applicationBaseName}
                        helperText={t("generalSection.nameOfApp.error")}
                        value={appName}
                        fullWidth
                        onChange={event => setAppName(event.target.value)}
                    />
                    <div className={generalSectionStyles.padding}/>
                    <ValidatedTextField
                        label={t("generalSection.packageNameOfApp")}
                        regex={RegexConfig.packageName}
                        helperText={t("generalSection.packageNameOfApp.error")}
                        value={packageName}
                        fullWidth
                        onChange={event => setPackageName(event.target.value)}
                    />
                    <MultipleSelectChip 
                    values={userGroups} 
                    selected={assignedGroups} 
                    handleExternalChange={handleGroupChange}
                    />
                </Grid>
                <Grid item md={4}>
                    <Typography variant={"h3"} gutterBottom>{t("generalSection.hello")}</Typography>
                    <Typography variant={"body1"} gutterBottom>{t("generalSection.enterInformation")}</Typography>
                    <Typography variant={"body1"} gutterBottom>{t("generalSection.clickNext")}</Typography>
                </Grid>
            </Grid>

        </Container>
    )

}

export default GeneralSection;