import React, {useState} from "react";
import {
    Container,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    Grid,
    List,
    ListItem,
    ListItemText,
    Skeleton,
    Typography
} from "@mui/material";
import EntityDiagram from "../entities/entityDiagram/EntityDiagram";
import PropTypes from "prop-types";
import AppStepsStyles from "./AppStepsStyles";
import {useTranslation} from "react-i18next";
import GitRest from "../../../services/GitRest";
import {Download} from "@mui/icons-material";
import GitDataButton from "../../../commons/gitDownloadButton/GitDataButton";
import {useSnackbar} from "notistack";
import Button from "@mui/material/Button";
import {docco} from "react-syntax-highlighter/dist/cjs/styles/hljs";
import SyntaxHighlighter from "react-syntax-highlighter";

function AppConclusion(props) {
    const {entities, appId, coordinates, appName, packageName, templateName, credentialsRequired} = props;
    const appStepsStyles = AppStepsStyles();
    const gitRest = new GitRest();
    const {t} = useTranslation();
    const {enqueueSnackbar} = useSnackbar();
    const [generatorErrorMessage, setGeneratorErrorMessage] = useState(null);

    function openInformationDialog(errorMessage) {
        setGeneratorErrorMessage(errorMessage);
    }

    function closeInformationDialog() {
        setGeneratorErrorMessage(null);
    }

    const handleGit = downloadRequestData => {
        return gitRest.setupApp(appId, downloadRequestData).catch(error => {
            if (error.response.data.messageKey === "error.generation.template") {
                setTimeout(() => {
                    enqueueSnackbar(t("appConclusion.generationError.snackbar"), {
                        action: key => <Button
                            onClick={() => openInformationDialog(error.response.data.message)}>{t("button.showMore")}</Button>
                    });
                }, 1000);
            }
        });
    };

    const handleAfterGitSuccess = () => {
        const link = document.createElement("a");
        link.href = window.location.pathname + "api/git/download-app/" + appId;
        link.download = appName + ".zip";
        link.click();
        document.body.removeChild(link);
    };

    function renderLoadingText(text) {
        if (!text) {
            return <Skeleton animation={"wave"}/>;
        }
        return (
            <Typography variant={"h6"} className={appStepsStyles.listItemText}>
                {text}
            </Typography>
        );
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
                            <ListItemText primary={renderLoadingText(templateName)}
                                secondary={t("app.section.template")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(packageName)}
                                secondary={t("app.packageName")}/>
                        </ListItem>
                    </List>
                </Grid>
                <Grid item md={3} className={appStepsStyles.downloadGrid}>
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
            <Typography variant={"h4"} gutterBottom>
                {t("app.section.entityDiagram")}
            </Typography>
            <EntityDiagram entities={entities} appId={+appId} coordinates={coordinates} dense editable={false}/>
            <Dialog onClose={closeInformationDialog} open={!!generatorErrorMessage} fullWidth maxWidth={"xl"}>
                <DialogTitle>{t("appConclusion.generationError.title")}</DialogTitle>
                <DialogContent>
                    <DialogContentText>{t("appConclusion.generationError.message")}</DialogContentText>
                    <SyntaxHighlighter
                        style={docco}
                        showLineNumbers
                        customStyle={{
                            lineHeight: "1.5",
                            fontSize: ".75em"
                        }}
                    >
                        {generatorErrorMessage ? generatorErrorMessage : ""}
                    </SyntaxHighlighter>
                </DialogContent>
                <DialogActions>
                    <Button onClick={closeInformationDialog} autoFocus>
                        {t("button.close")}
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}

AppConclusion.propTypes = {
    appId: PropTypes.number,
    entities: PropTypes.array,
    appName: PropTypes.string,
    packageName: PropTypes.string,
    templateName: PropTypes.string,
    coordinates: PropTypes.array,
    credentialsRequired: PropTypes.bool
};

AppConclusion.defaultProps = {
    appName: <Skeleton animation={"wave"}/>,
    templateName: <Skeleton animation={"wave"}/>,
    packageName: <Skeleton animation={"wave"}/>
};

export default AppConclusion;
