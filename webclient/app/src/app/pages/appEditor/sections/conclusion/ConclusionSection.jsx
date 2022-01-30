import React from "react";
import {Skeleton, Typography, Container, Grid, List, ListItem, ListItemText} from "@mui/material";
import ErSection from "../erSection/ErSection";
import PropTypes from "prop-types";
import ConclusionSectionStyles from "./ConclusionSectionStyles";
import {useTranslation} from "react-i18next";
import GitRest from "../../../../services/GitRest";
import { Download } from "@mui/icons-material";
import GitDataButton from "../../../../commons/gitDownloadButton/GitDataButton";

function ConclusionSection(props) {

    const {entities, coordinates, updateCoordinates, appId, appName, packageName, templateName, credentialsRequired} = props;
    const conclusionSectionStyles = ConclusionSectionStyles();
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
            <Typography variant={"h4"} gutterBottom>{t("app.erdiagram")}</Typography>
            <ErSection entities={entities} 
                coordinates={coordinates}
                updateCoordinates={updateCoordinates} 
                dense editable={false}/>
        </Container>
    )
}


ConclusionSection.propTypes = {
    appId: PropTypes.number,
    entities: PropTypes.array,
    appName: PropTypes.string,
    packageName: PropTypes.string,
    templateName: PropTypes.string,
    credentialsRequired: PropTypes.bool
}

ConclusionSection.defaultProps = {
    appName: <Skeleton animation={"wave"}/>,
    templateName: <Skeleton animation={"wave"}/>,
    packageName: <Skeleton animation={"wave"}/>,
}


export default ConclusionSection
