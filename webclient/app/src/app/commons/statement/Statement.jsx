import React from "react";
import StatementStyles from "./StatementStyles";
import {Typography} from "@mui/material";
import PropTypes from "prop-types";
import {CheckBoxOutlineBlank} from "@mui/icons-material";

function Statement(props) {

    const statementStyles = StatementStyles()
    const {icon, message} = props;


    return (
        <div className={statementStyles.root}>
            <div className={statementStyles.content}>
                {React.cloneElement(icon, {className: statementStyles.icon})}
                <Typography className={statementStyles.message}>{message}</Typography>
            </div>
        </div>
    )

}

Statement.propTypes = {
    message: PropTypes.string,
    icon: PropTypes.element
}

Statement.defaultProps = {
    message: "Keine Nachricht festgelegt",
    icon: <CheckBoxOutlineBlank/>
}

export default Statement