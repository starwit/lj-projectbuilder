import React, { useState } from "react";
import {
    Button,
    Card,
    CardActionArea,
    CardContent,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    IconButton,
    Snackbar,
    Grid,
    Typography,
    CardActions,
    Divider
} from "@mui/material";
import { Delete, Edit, MoreHoriz } from "@mui/icons-material";
import PropTypes from "prop-types";
import { useHistory } from "react-router-dom";
import AppCardStyles from "./AppCardStyles";
import { useTranslation } from "react-i18next";
import { Alert, LoadingButton } from "@mui/lab";

function AppCard(props) {

    const { app, onDeleteClick, onEditClick } = props;
    const history = useHistory();
    const appCardStyles = AppCardStyles();
    const { t } = useTranslation();

    const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
    const [isDeleting, setIsDeleting] = useState(false);
    const [isDeletingError, setIsDeletingError] = useState(null);

    function prepareDelete() {
        setIsDeleting(true)
        onDeleteClick(app.id)
            .then(response => {
                setOpenDeleteDialog(false);
                setIsDeleting(false);
            })
            .catch(response => setIsDeleting(response.data));
    }

    return (
        <>
            <Card elevation={5}>
                <CardContent>
                    <Grid container spacing={0}>
                        <Grid item xs={7}>
                            <Typography gutterBottom variant="h5" component="div">
                                {app.baseName}
                            </Typography>
                        </Grid>
                        <Grid item xs={5} align="right">
                                <IconButton onClick={onEditClick}><Edit fontSize={"small"} /></IconButton>
                                <IconButton onClick={() => setOpenDeleteDialog(true)}><Delete fontSize={"small"} /></IconButton>
 
                        </Grid>
                    </Grid>
                </CardContent>
                <Divider />
                <CardActionArea onClick={() => history.push("/apps/" + app.id)}>
                    <CardContent>
                        <Typography variant="body2" className={appCardStyles.description}>
                            {app.packageName}
                        </Typography>
                        <CardActions className={appCardStyles.cardActions}>
                            <MoreHoriz className={appCardStyles.cardActions} color="primary" />
                        </CardActions>
                    </CardContent>
                </CardActionArea>
            </Card>
            <Snackbar open={!!isDeletingError} autoHideDuration={6000} onClose={() => setIsDeletingError(null)}>
                <Alert onClose={() => setIsDeletingError(null)} severity="error">
                    {JSON.stringify(isDeletingError)}
                </Alert>
            </Snackbar>
            <Dialog
                open={openDeleteDialog}
                onClose={() => setOpenDeleteDialog(false)}
            >
                <DialogTitle>
                    {t("app.delete.title")}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        {t("app.delete.message")}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpenDeleteDialog(true)}>{t("button.cancel")}</Button>
                    <LoadingButton loading={isDeleting} onClick={prepareDelete} autoFocus>
                        {t("button.delete")}
                    </LoadingButton>
                </DialogActions>
            </Dialog>
        </>
    )
}

AppCard.propTypes = {
    app: PropTypes.shape({
        id: PropTypes.number.isRequired,
        baseName: PropTypes.string.isRequired,
        packageName: PropTypes.string.isRequired
    }),
    onEditClick: PropTypes.func.isRequired,
    onDeleteClick: PropTypes.func.isRequired
}


export default AppCard;