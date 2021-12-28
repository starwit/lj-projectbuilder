import React, { useState, useEffect } from "react";
import { styled } from '@mui/material/styles';
import { Container, Collapse, Card, CardContent, CardActions, List, ListItem, ListItemIcon, ListItemText, Typography, Divider, Grid, IconButton } from "@mui/material";
import AppTemplateCardStyles from "./AppTemplateCardStyles";
import { useTranslation } from "react-i18next";
import GitHub from '@mui/icons-material/GitHub';
import { Delete, Edit } from "@mui/icons-material";
import SyntaxHighlighter from "react-syntax-highlighter/dist/esm/light";
import js from 'react-syntax-highlighter/dist/esm/languages/hljs/javascript';
import dark from 'react-syntax-highlighter/dist/esm/styles/hljs/dark';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import AppTemplateDialog from "../../commons/appTemplateDialog/AppTemplateDialog";
import AppTemplateRest from "../../services/AppTemplateRest"
import ConfirmationDialog from "../alert/ConfirmationDialog";

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

    const { appTemplate, handleRefresh } = props;
    const appTemplateCardStyles = AppTemplateCardStyles();
    const { t } = useTranslation();
    const [expanded, setExpanded] = React.useState(false);
    const [selectedAppTemplate, setSelectedAppTemplate] = useState(false);
    const [openDialog, setOpenDialog] = React.useState(false);
    const appTemplateRest = new AppTemplateRest();

    const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
    const deleteDialolgContent = ({ "title": t("appTemplateDeleteDialog.title"), "message": t("appTemplateDeleteDialog.message") });

    const closeDeleteDialog = () => {
        setOpenDeleteDialog(false);
    }

    const handleExpandClick = () => {
        setExpanded(!expanded);
    };

    const handleDialogOpen = () => {
        setOpenDialog(true);
        setSelectedAppTemplate(appTemplate);
    };

    const handleDialogClose = () => {
        setOpenDialog(false);
    };

    const  handleDelete = (appTemplateId) => {
        appTemplateRest.delete(appTemplateId).then(response => {
            handleRefresh();
        });
    }


    useEffect(() => {
        SyntaxHighlighter.registerLanguage('javascript', js);
    },[]);

    return (
        <Container>
            <Card color={"error"}>
                <CardContent className={appTemplateCardStyles.CardContent}>
                    <Grid container spacing={0}>
                        <Grid item xs={7}>
                            <Typography gutterBottom variant="h5" component="span">
                                {appTemplate.name}
                            </Typography>
                        </Grid>
                        <Grid item xs={5} align="right">
                            <IconButton onClick={handleDialogOpen}><Edit /></IconButton>
                            <IconButton onClick={() => setOpenDeleteDialog(true)}><Delete /></IconButton>
                        </Grid>
                    </Grid>
                    <Divider />
                    <Typography variant="body2" color="text.secondary">
                        <br />{appTemplate.description}
                    </Typography>
                    <List>
                        <ListItem disablePadding>
                            <ListItemIcon>
                                <GitHub />
                            </ListItemIcon>
                            <ListItemText primary={appTemplate.location} secondary={t("templateCard.branch") + ": " + appTemplate.branch} />
                        </ListItem>
                    </List>
                </CardContent>
                <CardActions className={appTemplateCardStyles.actionsWrapper}>
                    <ExpandMore
                        expand={expanded}
                        onClick={handleExpandClick}
                        aria-expanded={expanded}
                        aria-label="show more" >
                        <ExpandMoreIcon />
                    </ExpandMore>
                </CardActions>
                <Collapse in={expanded} timeout="auto" unmountOnExit>
                    <CardContent>
                        <SyntaxHighlighter language="json" style={dark}>
                            {JSON.stringify(appTemplate, null, '\t')}
                        </SyntaxHighlighter>
                    </CardContent>
                </Collapse>
            </Card>
            <AppTemplateDialog
                appTemplate={selectedAppTemplate}
                open={openDialog}
                onClose={handleDialogClose}
                onRefresh={handleRefresh}
                isCreateDialog={false}
            />
            <ConfirmationDialog 
                content={deleteDialolgContent} 
                open={openDeleteDialog} 
                onClose={closeDeleteDialog} 
                onSubmit={() => handleDelete(appTemplate.id)} 
            />
        </Container>
    )
}

export default AppTemplateCard;