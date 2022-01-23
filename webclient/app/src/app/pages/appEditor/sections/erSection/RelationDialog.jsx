import React, {} from "react";
import {
    Box,
    Container,
    Dialog,
    DialogActions,
    DialogTitle,
    IconButton,
    Typography
} from "@mui/material";
import { Close } from "@mui/icons-material";
import {useTranslation} from "react-i18next";

import RelationDialogStyles from "./RelationDialogStyles";

function RelationDialog(props) {
    const { open } = props;
    const relationDialogStyles = RelationDialogStyles();
    const {t} = useTranslation();

    return (
        <Dialog open={open} maxWidth={"xl"} fullWidth>
            <DialogTitle className={relationDialogStyles.dialogHeaderBar}>
                <Typography noWrap variant={"h6"} component={"p"}>{t("entityDialog.editDomain")}</Typography>
                <div className={relationDialogStyles.flex}/>
                <IconButton
                    aria-label="close"
                >
                    <Close/>
                </IconButton>
            </DialogTitle>
            <Container>
                <Box className={relationDialogStyles.tabBox}>
                </Box>
                <DialogActions>
                </DialogActions>
            </Container>
        </Dialog>
    )
}

export default RelationDialog;