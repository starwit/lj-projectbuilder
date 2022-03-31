import React, {useState} from "react";
import {Card, CardActionArea, CardActions, CardContent, Divider, Grid, IconButton, Typography} from "@mui/material";
import {Delete, Edit, MoreHoriz} from "@mui/icons-material";
import PropTypes from "prop-types";
import {useHistory} from "react-router-dom";
import AppCardStyles from "./AppCardStyles";
import {useTranslation} from "react-i18next";
import ConfirmationDialog from "../../../commons/alert/ConfirmationDialog";

function AppCard(props) {
    const {app, onDeleteClick, onEditClick} = props;
    const history = useHistory();
    const appCardStyles = AppCardStyles();
    const {t} = useTranslation();

    const [openDeleteDialog, setOpenDeleteDialog] = useState(false);

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
                                <IconButton onClick={onEditClick}>
                                    <Edit fontSize={"small"}/>
                                </IconButton>
                                <IconButton onClick={() => setOpenDeleteDialog(true)}>
                                    <Delete fontSize={"small"}/>
                                </IconButton>
                            </Grid>
                        </Grid>
                    </CardContent>
                    <Divider/>
                    <CardActionArea onClick={() => history.push("/apps/" + app.id)}>
                        <CardContent>
                            <Typography variant="body2" className={appCardStyles.description}>
                                {app.packageName}
                            </Typography>
                            <CardActions className={appCardStyles.cardActions}>
                                <MoreHoriz className={appCardStyles.cardActions} color="primary"/>
                            </CardActions>
                        </CardContent>
                    </CardActionArea>
                </Card>
                <ConfirmationDialog
                        title={t("app.delete.title")}
                        message={t("app.delete.message")}
                        open={openDeleteDialog}
                        onClose={() => setOpenDeleteDialog(false)}
                        onSubmit={() => onDeleteClick(app.id)}
                        confirmTitle={t("button.delete")}
                />
            </>
    );
}

AppCard.propTypes = {
    app: PropTypes.shape({
        id: PropTypes.number.isRequired,
        baseName: PropTypes.string.isRequired,
        packageName: PropTypes.string.isRequired
    }),
    onEditClick: PropTypes.func.isRequired,
    onDeleteClick: PropTypes.func.isRequired
};

export default AppCard;
