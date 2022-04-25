import React, {useEffect} from "react";
import {
    Box,
    Button,
    Checkbox,
    Dialog,
    DialogActions,
    DialogTitle,
    FormControlLabel,
    FormGroup,
    IconButton,
    TextField,
    Typography
} from "@mui/material";
import AppTemplateDialogStyles from "./AppTemplateDialogStyles";
import {useTranslation} from "react-i18next";
import {Close} from "@mui/icons-material";
import AppTemplateRest from "../../../services/AppTemplateRest";
import ValidatedTextField from "../../../commons/inputFields/validatedTextField/ValidatedTextField";
import RegexConfig from "../../../../regexConfig";
import MultipleSelectChip from "../../../commons/inputFields/multipleSelectChip/MultipleSelectChip";
import {useImmer} from "use-immer";

function AppTemplateDialog(props) {
    const {appTemplate, open, onClose, onRefresh, isCreateDialog, userGroups} = props;
    const {t} = useTranslation();
    const [internalAppTemplate, setInternalAppTemplate] = useImmer(null);
    const [hasFormError, setHasFormError] = React.useState(false);
    const appTemplateDialogStyles = AppTemplateDialogStyles();
    const appTemplateRest = new AppTemplateRest();

    function onDialogClose() {
        onClose();
        setInternalAppTemplate(appTemplate);
    }

    function handleChange(event) {
        const {name, value} = event.target;
        setInternalAppTemplate(draft => {draft[name] = value;});
    }

    function handleGroupChange(items) {
        setInternalAppTemplate(draft => {draft["groups"] = items;});
    }

    function handleCredentialsCheckbox(event) {
        const {name, checked} = event.target;
        setInternalAppTemplate(draft => {draft[name] = checked;});
    }

    function handleSave() {
        if (hasFormError) {
            return;
        }
        setInternalAppTemplate(draft => {draft.userGroups = userGroups;});
        if (isCreateDialog) {
            appTemplateRest.create(internalAppTemplate).then(response => {
                handleSaveResponse(response);
            });
        } else {
            appTemplateRest.update(internalAppTemplate).then(response => {
                handleSaveResponse(response);
            });
        }
    }

    function handleSaveResponse(response) {
        setInternalAppTemplate(response.data);
        onRefresh();
        onClose();
    }

    useEffect(() => {
        if (!internalAppTemplate) {
            return;
        }
        let hasError = false;
        if (!RegexConfig.appTemplateLocation.test(internalAppTemplate.location)) {
            hasError = true;
        }
        if (!RegexConfig.appTemplateBranch.test(internalAppTemplate.branch)) {
            hasError = true;
        }
        setHasFormError(hasError);
    }, [internalAppTemplate, hasFormError]);

    useEffect(() => {
        setInternalAppTemplate(appTemplate);
    }, [appTemplate]);

    if (!appTemplate) {
        return null;
    }

    function insertTitle() {
        if (isCreateDialog) {
            return (<Typography noWrap variant={"h6"} component={"p"}>{t("apptemplate.new")}</Typography>);
        } else {
            return (<Typography noWrap variant={"h6"}
                component={"p"}>{t("apptemplate.edit", {appTemplateName: internalAppTemplate.name})}</Typography>);
        }
    }

    return (
        <Dialog onClose={onDialogClose} open={open} spacing={2}>
            <DialogTitle className={appTemplateDialogStyles.dialogHeaderBar}>
                {insertTitle()}
                <div className={appTemplateDialogStyles.flex}/>
                <IconButton
                    aria-label="close"
                    onClick={onDialogClose}
                >
                    <Close/>
                </IconButton>
            </DialogTitle>
            <Box
                component="form"
                sx={{
                    "& .MuiTextField-root": {m: 1, width: "95%"}

                }}
                noValidate
                autoComplete="off"
            >
                <ValidatedTextField
                    fullWidth
                    label={t("apptemplate.location") + "*"}
                    value={internalAppTemplate.location}
                    name="location"
                    onChange={handleChange}
                    isCreate={isCreateDialog}
                    helperText={t("apptemplate.location.hint")}
                    regex={RegexConfig.appTemplateLocation}
                />
                <ValidatedTextField
                    fullWidth
                    label={t("appTemplate.branch")}
                    value={internalAppTemplate.branch}
                    name="branch"
                    onChange={handleChange}
                    isCreate={isCreateDialog}
                    helperText={t("appTemplate.branch.hint")}
                    regex={RegexConfig.appTemplateBranch}
                />
                <TextField
                    fullWidth
                    label={t("appTemplate.description")}
                    value={internalAppTemplate.description}
                    name="description"
                    onChange={handleChange}
                />
                <FormGroup className={appTemplateDialogStyles.checkbox}>
                    <MultipleSelectChip
                        values={userGroups}
                        selected={internalAppTemplate.groups}
                        handleExternalChange={handleGroupChange}
                        label={t("select.groups")}/>
                    <FormControlLabel
                        control={
                            <Checkbox
                                checked={internalAppTemplate.credentialsRequired}
                                value={internalAppTemplate.credentialsRequired}
                                name="credentialsRequired" onChange={handleCredentialsCheckbox}
                            />
                        }
                        label={t("appTemplate.credentialsRequired")}
                    />
                </FormGroup>
                <DialogActions>
                    <Button onClick={onDialogClose}>{t("button.cancel")}</Button>
                    <Button disabled={hasFormError}
                        onClick={() => handleSave(internalAppTemplate)}>{t("button.save")}</Button>
                </DialogActions>
            </Box>
        </Dialog>
    );
}

export default AppTemplateDialog;
