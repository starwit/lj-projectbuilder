import {
    Alert,
    AlertTitle,
    Collapse,
} from "@mui/material";
import { useTranslation } from "react-i18next";

const ErrorAlert = (props) => {
    const { alert, onClose } = props;
    const { t } = useTranslation();

    return (
        <Collapse in={alert.open} onClose={onClose} >
        <Alert severity="error">
            <AlertTitle>{alert.title}</AlertTitle>
            {alert.message}
        </Alert>
        </Collapse>
    );
}

export default ErrorAlert;