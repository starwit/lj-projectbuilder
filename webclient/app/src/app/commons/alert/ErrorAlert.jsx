import {Alert, AlertTitle, Collapse} from "@mui/material";

const ErrorAlert = props => {
    const {alert, onClose, open, title, message} = props;

    return (
        <Collapse in={alert ? alert.open : open} onClose={onClose}>
            <Alert severity="error">
                <AlertTitle>{alert ? alert.title : title}</AlertTitle>
                {alert ? alert.message : message}
            </Alert>
        </Collapse>
    );
};

export default ErrorAlert;
