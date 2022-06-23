import React from "react";
import {Container, Grid, Stack, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";
import {MultipleSelectChip, ValidatedTextField} from "@starwit/react-starwit";
import RegexConfig from "../../../../regexConfig";

function AppGeneral(props) {
    const {t} = useTranslation();
    const {
        value,
        userGroups,
        onChange
    } = props;
    const groupsEvent = {target: {name: "assignedGroups", value: null}};

    return (
        <Container maxWidth={false}>
            <Grid container spacing={5}>
                <Grid item md={8}>
                    <Stack spacing={3}>
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
                        <MultipleSelectChip
                            values={userGroups}
                            selected={value.assignedGroups}
                            handleExternalChange={items => {groupsEvent.target.value = items; onChange(groupsEvent);}}
                            label={t("select.groups")}
                        />
                    </Stack>
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
