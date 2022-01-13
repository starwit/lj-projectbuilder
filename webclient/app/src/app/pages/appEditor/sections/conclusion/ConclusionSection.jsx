import React from "react";
import {Button, Container, Grid, List, ListItem, ListItemText, Skeleton, Typography} from "@mui/material";
import {Download} from "@mui/icons-material";
import ErSection from "../erSection/ErSection";
import PropTypes from "prop-types";
import ConclusionSectionStyles from "./ConclusionSectionStyles";
import {useTranslation} from "react-i18next";
import AppSetupRest from "../../../../services/AppSetupRest";

function ConclusionSection(props) {

    const {entities, appName, packageName, templateName} = props;
    const conclusionSectionStyles = ConclusionSectionStyles();
    const appSetupRest = new AppSetupRest();

    const {t} = useTranslation();

    const handleAppDownload = (toSave) => {
        appSetupRest.downloadApp(toSave).then(response => {
            handleDownloadResponse(response);
        }).catch(err => {
            setAlert({"open":true, "title": t("alert.error"), "message": JSON.stringify(err.response.data)});
        });
    }

    const handleDownloadResponse = (response) => {
        //TODO
    }

    function renderLoadingText(text) {
        if (!text) {
            return <Skeleton animation={"wave"}/>
        }
        return <Typography
            variant={"h6"}
            className={conclusionSectionStyles.listItemText}
        >
            {text}
        </Typography>;
    }

    return (
        <Container className={conclusionSectionStyles.root}>
            <Grid container spacing={5}>
                <Grid item md={9}>
                    <List className={conclusionSectionStyles.list}>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(appName)} secondary={t("generalSection.nameOfApp")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(templateName)} secondary={t("appEditor.section.template.title")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(packageName)} secondary={t("generalSection.packageNameOfApp")}/>
                        </ListItem>
                    </List>
                </Grid>
                <Grid
                    item
                    md={3}
                    className={conclusionSectionStyles.downloadGrid}
                >
                    <Button
                        startIcon={<Download/>}
                        color={"primary"}
                        variant={"contained"}
                        size={"large"}
                        onClick={() => handleAppDownload(internalAppTemplate)}
                    >
                        {t("button.download")}
                    </Button>
                </Grid>

            </Grid>
            <Typography variant={"h4"} gutterBottom>{t("app.erdiagram")}</Typography>
            <ErSection entities={entities} dense editable={false}/>
        </Container>
    )
}

ConclusionSection.propTypes = {
    entities: PropTypes.array,
    appName: PropTypes.string,
    packageName: PropTypes.string,
    templateName: PropTypes.string
}

ConclusionSection.defaultProps = {
    appName: <Skeleton animation={"wave"}/>,
    templateName: <Skeleton animation={"wave"}/>,
    packageName: <Skeleton animation={"wave"}/>,
}

export default ConclusionSection
