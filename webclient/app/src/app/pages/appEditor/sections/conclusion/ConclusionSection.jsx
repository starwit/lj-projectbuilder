import React from "react";
import {Skeleton, Typography, Container, Grid, List, ListItem, ListItemText} from "@mui/material";
import ErSection from "../erSection/ErSection";
import PropTypes from "prop-types";
import ConclusionSectionStyles from "./ConclusionSectionStyles";
import {useTranslation} from "react-i18next";
import GitRest from "../../../../services/GitRest";
import GitDataButton from "../../../../commons/gitDownloadButton/GitDataButton";

function ConclusionSection(props) {

    const {app} = props;
    const conclusionSectionStyles = ConclusionSectionStyles();
    const gitRest = new GitRest();
    const {t} = useTranslation();

    const handleGit = () => {
        return gitRest.setupApp();
    }

    const handleAfterGitSuccess = () => {
        gitRest.downloadApp();
    };

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
                            <ListItemText primary={renderLoadingText(app.baseName)} secondary={t("generalSection.nameOfApp")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(app.template.name)} secondary={t("appEditor.section.template.title")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(app.packageName)} secondary={t("generalSection.packageNameOfApp")}/>
                        </ListItem>
                    </List>
                </Grid>
                <Grid
                    item
                    md={3}
                    className={conclusionSectionStyles.downloadGrid}
                >
                    <GitDataButton appTemplate={app.template} handleAfter={handleAfterGitSuccess} handleGit={handleGit} buttonName={t("button.download")} />
                </Grid>

            </Grid>
            <Typography variant={"h4"} gutterBottom>{t("app.erdiagram")}</Typography>
            <ErSection entities={app.entities} dense editable={false}/>
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
