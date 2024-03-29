import {Delete, Edit} from "@mui/icons-material";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import GitHub from "@mui/icons-material/GitHub";
import {
    Box,
    Card,
    CardContent,
    Chip,
    Collapse,
    Container,
    Divider,
    Grid,
    IconButton,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    Stack,
    Typography
} from "@mui/material";
import {styled} from "@mui/material/styles";
import {ConfirmationDialog, GitDataButton} from "@starwit/react-starwit";
import {useSnackbar} from "notistack";
import React, {useEffect, useState} from "react";
import {useTranslation} from "react-i18next";
import js from "react-syntax-highlighter/dist/esm/languages/hljs/javascript";
import SyntaxHighlighter from "react-syntax-highlighter/dist/esm/light";
import docco from "react-syntax-highlighter/dist/esm/styles/hljs/docco";
import AppTemplateRest from "../../../services/AppTemplateRest";
import GitRest from "../../../services/GitRest";
import AppTemplateDialog from "./../appTemplateDialog/AppTemplateDialog";
import AppTemplateCardStyles from "./AppTemplateCardStyles";

const ExpandMore = styled(props => {
    const {...other} = props;
    return <IconButton {...other} />;
})(({theme, expand}) => ({
    transform: !expand ? "rotate(0deg)" : "rotate(180deg)",
    marginLeft: "auto",
    transition: theme.transitions.create("transform", {
        duration: theme.transitions.duration.shortest
    })
}));

function AppTemplateCard(props) {
    const appTemplateCardStyles = AppTemplateCardStyles();
    const {t} = useTranslation();
    const {enqueueSnackbar} = useSnackbar();

    const {appTemplate, handleRefresh, userGroups} = props;
    const appTemplateRest = new AppTemplateRest();
    const gitRest = new GitRest();
    const [selectedAppTemplate, setSelectedAppTemplate] = useState(false);
    const [extendedAppTemplate, setExtendedAppTemplate] = useState(false);

    const [expanded, setExpanded] = React.useState(false);
    const [openDialog, setOpenDialog] = React.useState(false);

    const [openDeleteDialog, setOpenDeleteDialog] = useState(false);

    const closeDeleteDialog = () => {
        setOpenDeleteDialog(false);
    };

    function handleExpandClick(appTemplateId) {
        loadAppTemplate(appTemplateId);
        setExpanded(!expanded);
    }

    function handleDialogOpen() {
        setOpenDialog(true);
        setSelectedAppTemplate(appTemplate);
    }

    function handleDialogClose() {
        setOpenDialog(false);
    }

    function handleDelete(appTemplateId) {
        return appTemplateRest.delete(appTemplateId).then(() => {
            handleRefresh();
        });
    }

    function handleGit(downloadRequestData) {
        return gitRest.updateTemplates(appTemplate.id, downloadRequestData);
    }

    function handleAfterGitSuccess() {
        handleRefresh();
        enqueueSnackbar(t("appTemplate.success.message"), {variant: "success"});
    }

    function loadAppTemplate(appTemplateId) {
        const appTemplateRest2 = new AppTemplateRest();
        appTemplateRest2.findById(appTemplateId).then(response => {
            setExtendedAppTemplate(response.data);
        });
    }

    useEffect(() => {
        SyntaxHighlighter.registerLanguage("javascript", js);
    }, []);

    return (
        <Container disableGutters={true}>
            <Card color={"error"}>
                <CardContent className={appTemplateCardStyles.CardContent}>
                    <Grid container spacing={0}>
                        <Grid item xs={7}>
                            <Typography gutterBottom variant="h5" component="span">
                                {appTemplate.name}
                            </Typography>
                        </Grid>
                        <Grid item xs={5} align="right">
                            <IconButton onClick={handleDialogOpen}>
                                <Edit fontSize={"small"} />
                            </IconButton>
                            <IconButton onClick={() => setOpenDeleteDialog(true)}>
                                <Delete fontSize={"small"} />
                            </IconButton>
                        </Grid>
                    </Grid>
                </CardContent>
                <Divider />
                <CardContent>
                    <Grid container spacing={0} alignItems={"left"}>
                        <Grid item sm={7}>
                            <Stack spacing={2}>
                                <Typography variant="body2" color="text.secondary" component="span">
                                    <Box>{appTemplate.description}</Box>
                                    <Box>{t("appTemplate.configFile") + ": " + appTemplate.configFile}</Box>
                                </Typography>
                                <List>
                                    <ListItem disablePadding>
                                        <ListItemIcon>
                                            <GitHub />
                                        </ListItemIcon>
                                        <ListItemText primary={appTemplate.location}
                                            secondary={t("appTemplate.branch") + ": " + appTemplate.branch} />
                                    </ListItem>
                                </List>
                            </Stack>
                        </Grid>
                        <Grid item xs={5} align="right">
                            <GitDataButton
                                credentialsRequired={appTemplate.credentialsRequired}
                                handleAfterSuccess={handleAfterGitSuccess}
                                handleGit={handleGit}
                                buttonName={t("button.loadtemplate")}
                            />
                        </Grid>
                    </Grid>
                </CardContent>

                <Divider>
                    <Chip label={t("appTemplate.config")} />
                    <ExpandMore expand={expanded} onClick={() => handleExpandClick(appTemplate.id)}
                        aria-expanded={expanded} aria-label="show more">
                        <ExpandMoreIcon />
                    </ExpandMore>
                </Divider>
                <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <CardContent>
                        <SyntaxHighlighter
                            language="json"
                            style={docco}
                            showLineNumbers
                            customStyle={{
                                lineHeight: "1.5",
                                fontSize: ".75em"
                            }}
                        >
                            {JSON.stringify(extendedAppTemplate, null, "\t")}
                        </SyntaxHighlighter>
                    </CardContent>
                </Collapse>
            </Card>
            <AppTemplateDialog
                appTemplate={selectedAppTemplate}
                open={openDialog}
                onClose={handleDialogClose}
                onRefresh={() => handleRefresh(appTemplate.id)}
                isCreateDialog={false}
                userGroups={userGroups}
            />
            <ConfirmationDialog
                title={t("appTemplate.delete.title")}
                message={t("appTemplate.delete.message")}
                open={openDeleteDialog}
                onClose={closeDeleteDialog}
                onSubmit={() => handleDelete(appTemplate.id)}
                confirmTitle={t("button.delete")}
            />
        </Container>
    );
}

export default AppTemplateCard;
