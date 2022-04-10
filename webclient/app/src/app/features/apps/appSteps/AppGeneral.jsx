import React from "react";
import {Container, Grid, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import ValidatedTextField from "../../../commons/validatedTextField/ValidatedTextField";
import RegexConfig from "../../../../regexConfig";
import AppStepsStyles from "./AppStepsStyles";
import MultipleSelectChip from "../../../commons/multipleSelectChip/MultipleSelectChip";

function AppGeneral(props) {
    const {t} = useTranslation();
    const {
        value,
        userGroups,
        onChange
    } = props;

    const appStepsStyles = AppStepsStyles();

    return (
        <Container maxWidth={false}>
            <Grid container spacing={5}>
                <Grid item md={8}>
                    <ValidatedTextField
                        label={t("app.name") + "*"}
                        regex={RegexConfig.applicationBaseName}
                        helperText={t("app.name.hint")}
                        name="appName"
                        value={value.appName}
                        fullWidth
                        onChange={onChange}
                        isCreate={value.isNew}
                    />
                    <div className={appStepsStyles.padding}/>
                    <ValidatedTextField
                        label={t("app.packageName") + "*"}
                        regex={RegexConfig.packageName}
                        helperText={t("app.packageName.hint")}
                        name="packageName"
                        value={value.packageName}
                        fullWidth
                        onChange={onChange}
                        isCreate={value.isNew}
                    />
                    <div className={appStepsStyles.padding}/>
                    <MultipleSelectChip
                        values={userGroups}
                        selected={value.assignedGroups}
                        handleExternalChange={onChange}
                        label={t("select.groups")}
                    />
                </Grid>
                <Grid item md={4}>
                    <Typography variant={"h3"} gutterBottom>{t("app.section.general.hello")}</Typography>
                    <Typography variant={"body1"}
                        gutterBottom>{t("app.section.general.enterInformation")}</Typography>
                    <Typography variant={"body1"} gutterBottom>{t("app.section.general.clickNext")}</Typography>
                </Grid>
            </Grid>

        </Container>
    );
}

export default AppGeneral;
