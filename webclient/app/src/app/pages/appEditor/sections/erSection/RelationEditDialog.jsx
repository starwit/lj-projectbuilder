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

import RelationEditDialogStyles from "./RelationEditDialogStyles";

function RelationEditDialog(props) {
    const { open } = props;
    const relationEditDialogStyles = RelationEditDialogStyles();
    const {t} = useTranslation();

    return (
        <Dialog open={open} maxWidth={"xl"} fullWidth>
            <DialogTitle className={relationEditDialogStyles.dialogHeaderBar}>
                <Typography noWrap variant={"h6"} component={"p"}>{t("entityDialog.editDomain")}</Typography>
                <div className={relationEditDialogStyles.flex}/>
                <IconButton
                    aria-label="close"
                >
                    <Close/>
                </IconButton>
            </DialogTitle>
            <Container>
                <Box className={relationEditDialogStyles.tabBox}>
                </Box>
                <DialogActions>
                </DialogActions>
            </Container>
        </Dialog>
    )
}

export default RelationEditDialog;