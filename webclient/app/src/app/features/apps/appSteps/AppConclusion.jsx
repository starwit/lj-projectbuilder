import React from "react";
import {Skeleton, Typography, Container, Grid, List, ListItem, ListItemText} from "@mui/material";
import EntityDiagram from "../entities/entityDiagram/EntityDiagram";
import PropTypes from "prop-types";
import AppStepsStyles from "./AppStepsStyles";
import {useTranslation} from "react-i18next";
import GitRest from "../../../services/GitRest";
import { Download } from "@mui/icons-material";
import GitDataButton from "../../../commons/gitDownloadButton/GitDataButton";

function AppConclusion(props) {

    const {entities, appId, coordinates, appName, packageName, templateName, credentialsRequired} = props;
    const appStepsStyles = AppStepsStyles();
    const gitRest = new GitRest();
    const {t} = useTranslation();

    const handleGit = (downloadRequestData) => {
        return gitRest.setupApp(appId, downloadRequestData);
    }

    const handleAfterGitSuccess = () => {
        const link = document.createElement('a');
        link.href = window.location.pathname + "api/git/download-app/" + appId;
        link.download = appName + '.zip';
        link.click();
        document.body.removeChild(link);
    }

    function renderLoadingText(text) {
        if (!text) {
            return <Skeleton animation={"wave"}/>
        }
        return <Typography
            variant={"h6"}
            className={appStepsStyles.listItemText}
        >
            {text}
        </Typography>;
    }

    return (
        <Container className={appStepsStyles.root}>
            <Grid container spacing={5}>
                <Grid item md={9}>
                    <List className={appStepsStyles.list}>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(appName)} secondary={t("app.name")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(templateName)} secondary={t("app.section.template")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(packageName)} secondary={t("app.packageName")}/>
                        </ListItem>
                    </List>
                </Grid>
                <Grid
                    item
                    md={3}
                    className={appStepsStyles.downloadGrid}
                >
                    <GitDataButton 
                        credentialsRequired={credentialsRequired} 
                        handleAfterSuccess={handleAfterGitSuccess} 
                        handleGit={handleGit}
                        buttonIcon={<Download/>}
                        buttonName={t("button.download")}
                        buttonVariant={"contained"}
                    />
                </Grid>

            </Grid>
            <Typography variant={"h4"} gutterBottom>{t("app.section.entityDiagram")}</Typography>
            <EntityDiagram entities={entities} 
                appId={+appId}
                coordinates={coordinates}
                dense 
                editable={false}
                />
        </Container>
    )
}

AppConclusion.propTypes = {
    appId: PropTypes.number,
    entities: PropTypes.array,
    appName: PropTypes.string,
    packageName: PropTypes.string,
    templateName: PropTypes.string,
    coordinates: PropTypes.array,
    credentialsRequired: PropTypes.bool
}

AppConclusion.defaultProps = {
    appName: <Skeleton animation={"wave"}/>,
    templateName: <Skeleton animation={"wave"}/>,
    packageName: <Skeleton animation={"wave"}/>,
}


export default AppConclusion
