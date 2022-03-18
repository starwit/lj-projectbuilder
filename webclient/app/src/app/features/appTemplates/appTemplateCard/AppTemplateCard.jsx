import React, { useState, useEffect } from "react";
import { styled } from '@mui/material/styles';
import { Chip, Container, Collapse, Card, CardContent, List, ListItem, ListItemIcon, ListItemText, Typography, Divider, Grid, IconButton } from "@mui/material";
import AppTemplateCardStyles from "./AppTemplateCardStyles";
import { useTranslation } from "react-i18next";
import GitHub from '@mui/icons-material/GitHub';
import { Delete, Edit } from "@mui/icons-material";
import SyntaxHighlighter from "react-syntax-highlighter/dist/esm/light";
import js from 'react-syntax-highlighter/dist/esm/languages/hljs/javascript';
import docco from 'react-syntax-highlighter/dist/esm/styles/hljs/docco';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AppTemplateDialog from "./../appTemplateDialog/AppTemplateDialog";
import AppTemplateRest from "../../../services/AppTemplateRest";
import GitRest from "../../../services/GitRest";
import ConfirmationDialog from "../../../commons/alert/ConfirmationDialog";
import NotificationDialog from "../../../commons/alert/NotificationDialog";
import GitDataButton from "../../../commons/gitDownloadButton/GitDataButton";

const ExpandMore = styled((props) => {
    const { expand, ...other } = props;
    return <IconButton {...other} />;
})(({ theme, expand }) => ({
    transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
    }),
}));


function AppTemplateCard(props) {

    const appTemplateCardStyles = AppTemplateCardStyles();
    const { t } = useTranslation();

    const { appTemplate, handleRefresh, userGroups } = props;
    const appTemplateRest = new AppTemplateRest();
    const gitRest = new GitRest();
    const [selectedAppTemplate, setSelectedAppTemplate] = useState(false);
    const [extendedAppTemplate, setExtendedAppTemplate] = useState(false);

    const [expanded, setExpanded] = React.useState(false);
    const [openDialog, setOpenDialog] = React.useState(false);

    const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
    const deleteDialogContent = ({ "title": t("appTemplate.delete.title"), "message": t("appTemplate.delete.message") });

    const deleteErrorDialogContent = ({ "title": t("appTemplate.error.title"), "message": t("appTemplate.error.message") });
    const [openErrorDialog, setOpenErrorDialog] = useState(false);

    const successDialogContent = ({ "title": t("appTemplate.success.title"), "message": t("appTemplate.success.message") });
    const [openSuccessDialog, setOpenSuccessDialog] = useState(false);

    const closeDeleteDialog = () => {
        setOpenDeleteDialog(false);
    };

    const handleExpandClick = (appTemplateId) => {
        loadAppTemplate(appTemplateId);
        setExpanded(!expanded);
    };

    const handleDialogOpen = () => {
        setOpenDialog(true);
        setSelectedAppTemplate(appTemplate);
    };

    const handleDialogClose = () => {
        setOpenDialog(false);
    };

    const handleDelete = (appTemplateId) => {
        appTemplateRest.delete(appTemplateId)
            .then(() => {
                handleRefresh();
            }).catch(err => {
                setOpenErrorDialog(true);
                setOpenDeleteDialog(false);
                console.log(err.response.data);
            });
    };

    const handleGit = (downloadRequestData) => {
        return gitRest.updateTemplates(appTemplate.id, downloadRequestData);
    }

    const handleAfterGitSuccess = () => {
        handleRefresh();
        setOpenSuccessDialog(true);
    };

    const loadAppTemplate = (appTemplateId) => {
        const appTemplateRest2 = new AppTemplateRest();
        appTemplateRest2.findById(appTemplateId).then(response => {
            setExtendedAppTemplate(response.data);
        });
    };

    useEffect(() => {
        SyntaxHighlighter.registerLanguage('javascript', js);
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
                            <IconButton onClick={handleDialogOpen}><Edit  fontSize={"small"} /></IconButton>
                            <IconButton onClick={() => setOpenDeleteDialog(true)}><Delete  fontSize={"small"} /></IconButton>
                        </Grid>
                    </Grid>
                </CardContent>
                <Divider />
                <CardContent>
                    <Grid container spacing={0}>
                        <Grid item sm={7}>
                            <Typography variant="body2" color="text.secondary">
                                <br />{appTemplate.description}
                            </Typography>
                            <List>
                                <ListItem disablePadding>
                                    <ListItemIcon>
                                        <GitHub />
                                    </ListItemIcon>
                                    <ListItemText primary={appTemplate.location} secondary={t("appTemplate.branch") + ": " + appTemplate.branch} />
                                </ListItem>
                            </List>
                        </Grid>
                        <Grid item xs={5} align="right">
                            <br /><br />
                            <GitDataButton
                                credentialsRequired={appTemplate.credentialsRequired}
                                handleAfterSuccess={handleAfterGitSuccess}
                                handleGit={handleGit}
                                buttonName={t("button.loadtemplate")} />
                        </Grid>
                    </Grid>
                </CardContent>

                <Divider>
                    <Chip label={t("appTemplate.config")} />
                    <ExpandMore
                        expand={expanded}
                        onClick={() => handleExpandClick(appTemplate.id)}
                        aria-expanded={expanded}
                        aria-label="show more" >
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
                                lineHeight: "1.5", fontSize: ".75em"
                            }}>
                            {JSON.stringify(extendedAppTemplate, null, '\t')}
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
                content={deleteDialogContent}
                open={openDeleteDialog}
                onClose={closeDeleteDialog}
                onSubmit={
                    () => handleDelete(appTemplate.id)
                }
            />
            <NotificationDialog
                severity="error"
                content={deleteErrorDialogContent}
                open={openErrorDialog}
                onClose={() => setOpenErrorDialog(false)}
            />
            <NotificationDialog
                severity="success"
                content={successDialogContent}
                open={openSuccessDialog}
                onClose={() => setOpenSuccessDialog(false)}
            />
        </Container>
    )
}

export default AppTemplateCard;