import React, { useState } from "react";
import { Button, TextField, Container, DialogTitle, Typography } from '@mui/material';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import { useTranslation } from 'react-i18next';
import AppTemplateRest from "../../services/AppTemplateRest";
import { CloudSync } from "@mui/icons-material";
import ErrorAlert from "../alert/ErrorAlert";

function AppTemplateAuthDialog(props) {
    const { appTemplate, handleRefresh, setOpenSuccessDialog, setOpenErrorDialog } = props;
    const [downloadRequestData, setDownloadRequestData] = useState(false);
    const { t } = useTranslation();

    const appTemplateRest = new AppTemplateRest();

    const [openAuthDialog, setOpenAuthDialog] = useState(false); 
    const downloadTemplateDto = ({ "appTemplateId": null, "username": null, "password": null });


    const handleChange = (event) => {
        const { name, value } = event.target;
        let downloadRequestDataNew = { ...downloadRequestData };
        downloadRequestDataNew[name] = value;
        setDownloadRequestData(downloadRequestDataNew);
    }

    const handleLogin = () => {
        if(appTemplate.credentialsRequired) {
            setOpenAuthDialog(true);
        } else {
            handleAppTemplateReload();
        }
    }

    const handleAppTemplateReload = () => {
        downloadTemplateDto.appTemplateId = appTemplate.id;
        appTemplateRest.updateTemplates(downloadTemplateDto).then(() => {
            handleRefresh();
            setOpenAuthDialog(false);
            setOpenSuccessDialog(true);
        }).catch(err => {
            setOpenErrorDialog(true);
            console.log(err.response.data);
        });
    }
    
    const onClose = () => {
        setOpenAuthDialog(false);
    }

    return (
        <Container>
            <Button onClick={() => handleLogin} startIcon={<CloudSync />} >{t("button.loadtemplate")}</Button>
            <Dialog open={openAuthDialog} onClose={onClose}>
                <DialogTitle>{t("appTemplateAuthDialog.title")}</DialogTitle>
                <ErrorAlert alert={alert} onClose={() => setOpenErrorDialog(false)} />
                <Typography>{t("appTemplateAuthDialog.message")}</Typography>
                <TextField
                    fullWidth
                    label={t("appTemplateAuthDialog.user")}
                    value={downloadRequestData.user}
                    name="user"
                    onChange={handleChange}
                />
                <TextField
                    type="password"
                    fullWidth
                    label={t("appTemplateAuthDialog.password")}
                    value={downloadRequestData.password}
                    name="passord"
                    onChange={handleChange}
                />
                <DialogActions>
                    <Button onClick={onClose}>{t("button.cancel")}</Button>
                    <Button onClick={handleAppTemplateReload} autoFocus>
                        {t("button.ok")}
                    </Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
}

export default AppTemplateAuthDialog;