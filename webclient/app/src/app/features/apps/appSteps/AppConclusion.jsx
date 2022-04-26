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
import AppStepsStyles from "./AppStepsStyles";
import {useTranslation} from "react-i18next";
import GitRest from "../../../services/GitRest";
import {Download} from "@mui/icons-material";
import GitDataButton from "../../../commons/buttons/gitDownloadButton/GitDataButton";
import {useSnackbar} from "notistack";
import Button from "@mui/material/Button";
import {docco} from "react-syntax-highlighter/dist/cjs/styles/hljs";
import SyntaxHighlighter from "react-syntax-highlighter";

function AppConclusion(props) {
    const {app} = props;
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

    const showMoreOnErrors = [
        "error.generation.template",
        "error.generation.generatepath",
        "error.generation.generateglobal",
        "error.generation.templatenotfound"
    ];

    const handleGit = downloadRequestData => {
        return gitRest.setupApp(app.id, downloadRequestData).catch(error => {
            if (showMoreOnErrors.includes(error.response.data.messageKey)) {
                setTimeout(() => {
                    enqueueSnackbar(t("appConclusion.generationError.snackbar"), {
                        action: key => <Button
                            onClick={() =>
                                openInformationDialog(error.response.data.message)}>{t("button.showMore")}</Button>
                    });
                }, 1000);
            }
        });
    };

    const handleAfterGitSuccess = () => {
        const link = document.createElement("a");
        link.href = window.location.pathname + "api/git/download-app/" + app.id;
        link.download = app.general.appName + ".zip";
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
                            <ListItemText primary={renderLoadingText(app.general.appName)} secondary={t("app.name")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(app.template?.name)}
                                secondary={t("app.section.template")}/>
                        </ListItem>
                        <ListItem>
                            <ListItemText primary={renderLoadingText(app.general.packageName)}
                                secondary={t("app.packageName")}/>
                        </ListItem>
                    </List>
                </Grid>
                <Grid item md={3} className={appStepsStyles.downloadGrid}>
                    <GitDataButton
                        credentialsRequired={app.template.credentialsRequired}
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
            <EntityDiagram entities={app.entities} dense editable={false}/>
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

export default AppConclusion;
