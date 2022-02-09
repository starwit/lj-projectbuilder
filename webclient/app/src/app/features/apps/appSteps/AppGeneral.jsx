import React from "react";
import {Container, Grid, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import ValidatedTextField from "../../../commons/validatedTextField/ValidatedTextField";
import RegexConfig from "../../../../regexConfig";
import AppStepsStyles from "./AppStepsStyles";
import MultipleSelectChip from "../../../commons/multipleSelectChip/MultipleSelectChip";

function app.section.general(props) {

    const {t} = useTranslation();
    const {isCreate, setAppName, setPackageName, appName, packageName, userGroups, assignedGroups, setAssignedGroups} = props;
    const appStepsStyles = AppStepsStyles();

    const handleGroupChange = (items) => {
        setAssignedGroups(items);
    };


    return (
        <Container maxWidth={false}>
            <Grid container spacing={5}>
                <Grid item md={8}>
                    <ValidatedTextField
                        label={t("app.name") + "*"}
                        regex={RegexConfig.applicationBaseName}
                        helperText={t("app.name.hint")}
                        value={appName}
                        fullWidth
                        onChange={event => setAppName(event.target.value)}
                        isCreate={isCreate}
                    />
                    <div className={appStepsStyles.padding}/>
                    <ValidatedTextField
                        label={t("app.packageName") + "*"}
                        regex={RegexConfig.packageName}
                        helperText={t("app.packageName.hint")}
                        value={packageName}
                        fullWidth
                        onChange={event => setPackageName(event.target.value)}
                        isCreate={isCreate}
                    />
                    <div className={appStepsStyles.padding}/>
                    <MultipleSelectChip 
                        values={userGroups} 
                        selected={assignedGroups} 
                        handleExternalChange={handleGroupChange}
                        label={t("select.groups")}
                    />
                </Grid>
                <Grid item md={4}>
                    <Typography variant={"h3"} gutterBottom>{t("app.section.general.hello")}</Typography>
                    <Typography variant={"body1"} gutterBottom>{t("app.section.general.enterInformation")}</Typography>
                    <Typography variant={"body1"} gutterBottom>{t("app.section.general.clickNext")}</Typography>
                </Grid>
            </Grid>

        </Container>
    )

}

export default app.section.general;