import React from "react";
import StatementStyles from "./StatementStyles";
import {Button, Typography} from "@mui/material";
import PropTypes from "prop-types";
import iconEmpty from "./icon-empty.png";

function Statement(props) {
    const statementStyles = StatementStyles();
    const {icon, message, actionMessage, onActionClick} = props;

    function renderActionButton() {
        if (actionMessage && onActionClick) {
            return <Button onClick={onActionClick}>{actionMessage}</Button>;
        }
    }

    function renderIcon() {
        if (icon) {
            return React.cloneElement(icon, {fontSize: "large"});
        }
    }

    return (
            <div className={statementStyles.root}>
                <div className={statementStyles.content}>
                    {renderIcon()}
                    <Typography component={"span"} className={statementStyles.message}
                                gutterBottom>{message}</Typography>
                    {renderActionButton()}
                </div>
            </div>
    );
}

Statement.propTypes = {
    message: PropTypes.string,
    icon: PropTypes.element,
    actionMessage: PropTypes.string,
    onActionClick: PropTypes.func
};

Statement.defaultProps = {
    message: "Keine Nachricht festgelegt",
    icon: <img src={iconEmpty} alt="empty"/>
};

export default Statement;
