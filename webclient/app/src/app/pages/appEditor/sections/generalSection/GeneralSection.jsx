import React from "react";
import {Container, Grid, TextField, Typography} from "@mui/material";
import {useTranslation} from "react-i18next";

function GeneralSection(props) {

    const {t} = useTranslation();
    const {setAppName, setPackageName, appName, packageName} = props;
    return (
        <Container maxWidth={false}>
            <Grid container spacing={5}>
                <Grid item md={8}>
                    <TextField
                        label={t("generalSection.nameOfApp")}
                        value={appName}
                        fullWidth
                        onChange={event => setAppName(event.target.value)}
                    />
                    <div style={{paddingBottom: "2rem"}}/>
                    <TextField
                        label={t("generalSection.descriptionOfApp")}
                        value={packageName}
                        fullWidth
                        onChange={event => setPackageName(event.target.value)}
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