import React, {useEffect, useState} from "react";
import {
    Container,
    Dialog,
    DialogActions,
    DialogTitle,
    IconButton,
    Stack,
    Typography
} from "@mui/material";
import {Close} from "@mui/icons-material";
import {LoadingSpinner, ValidatedTextField} from "@starwit/react-starwit";
import {useTranslation} from "react-i18next";
import RegexConfig from "../../../../../regexConfig";
import {LoadingButton} from "@mui/lab";
import EnumDialogStyles from "./EnumDialogStyles";
import {useImmer} from "use-immer";

function EnumDialog(props) {
    const {enumDef, onClose, handleSave, open} = props;
    const [currEnum, setCurrEnum] = useImmer(null);
    const [hasFormError, setHasFormError] = useState(false);
    const [isSaving, setIsSaving] = useState(false);
    const enumDialogStyles = EnumDialogStyles();
    const {t} = useTranslation();

    useEffect(() => {
        setCurrEnum(enumDef);
    }, [enumDef]);

    useEffect(() => {
        if (!currEnum) {
            return;
        }
        let hasError = false;

        if (!RegexConfig.enumName.test(currEnum.name)) {
            hasError = true;
        }
        setHasFormError(hasError);
    }, [currEnum]);

    function prepareSave() {
        setIsSaving(true);
        handleSave(currEnum)
            .then(() => {
                onClose();
                setIsSaving(false);
            })
            .catch(() => {
                setIsSaving(false);
            });
    }

    function handleEnumName(event) {
        setCurrEnum(draft => {
            draft.name = event.target.value;
        });
    }

    function handleEnumValue(event) {
        setCurrEnum(draft => {
            draft.value = event.target.value;
        });
    }

    if (isSaving) {
        return <LoadingSpinner message={t("enum.loading")}/>;
    }

    return (
        <Dialog open={currEnum != null || open} maxWidth={"xl"} fullWidth>
            <DialogTitle className={enumDialogStyles.dialogHeaderBar + " " + enumDialogStyles.enumBg}>
                <Typography noWrap variant={"h6"} component={"p"}>
                    {t("entity.edit", {entityName: currEnum?.name})}
                </Typography>
                <div className={enumDialogStyles.flex}/>
                <IconButton aria-label="close" onClick={onClose}>
                    <Close/>
                </IconButton>
            </DialogTitle>
            <Container>
                <Stack spacing={2}>
                    <ValidatedTextField
                        isCreate={!currEnum || !currEnum.id }
                        fullWidth
                        label={t("enum.name") + "*"}
                        value={currEnum?.name}
                        onChange={handleEnumName}
                        helperText={t("enum.name.hint")}
                        regex={RegexConfig.enumName}
                    />
                    <ValidatedTextField
                        isCreate={!currEnum || !currEnum.id }
                        fullWidth
                        label={t("enum.value") + "*"}
                        value={currEnum?.value}
                        onChange={handleEnumValue}
                        helperText={t("enum.value.hint")}
                    />
                    <DialogActions>
                        <LoadingButton onClick={prepareSave} disabled={hasFormError} loading={isSaving}>
                            {t("button.save")}
                        </LoadingButton>
                    </DialogActions>
                </Stack>
            </Container>
        </Dialog>
    );
}

export default EnumDialog;
