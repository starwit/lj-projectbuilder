import React, {useState} from "react";
import {
    Button,
    Card,
    CardActionArea,
    CardActions,
    CardContent,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    IconButton,
    Snackbar,
    Typography
} from "@mui/material";
import {Delete} from "@mui/icons-material";
import PropTypes from "prop-types";
import {useHistory} from "react-router-dom";
import AppCardStyles from "./AppCardStyles";
import {useTranslation} from "react-i18next";
import {Alert, LoadingButton} from "@mui/lab";

function AppCard(props) {

    const {app, onDeleteClick, onEditClick} = props;
    const history = useHistory();
    const appCardStyles = AppCardStyles();
    const {t} = useTranslation();

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
                <CardActionArea onClick={() => history.push("/app/" + app.id)}>
                    <CardContent>
                        <Typography gutterBottom variant="h5" component="div">
                            {app.baseName}
                        </Typography>
                        <Typography variant="body2" className={appCardStyles.description}>
                            {app.packageName}
                        </Typography>
                    </CardContent>
                </CardActionArea>
                <CardActions className={appCardStyles.cardActions}>
                    <Button size="small" onClick={onEditClick}>{t("button.edit")}</Button>
                    <IconButton onClick={() => setOpenDeleteDialog(true)}><Delete fontSize={"small"}/></IconButton>
                </CardActions>
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
                    {t("appCard.sureToDelete.title")}
                </DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        {t("appCard.sureToDelete.message")}
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
        title: PropTypes.string.isRequired,
        description: PropTypes.string.isRequired
    }),
    onEditClick: PropTypes.func.isRequired,
    onDeleteClick: PropTypes.func.isRequired
}


export default AppCard;