import React from "react";
import {TextField} from "@mui/material";
import PropTypes from "prop-types";

function ValidatedTextField(props) {

    const {value, regex, overwriteError} = props;

    function isError() {
        return overwriteError || regex.test(value);
    }

    return (
        <TextField
            error={isError()}
            value={value}
            handleChange
            {...props}
        />
    )

}

ValidatedTextField.defaultProps = {
    value: "",
    overwriteError: false
}

ValidatedTextField.propTypes = {
    helperText: PropTypes.string.isRequired,
    regex: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
    value: PropTypes.string,
    overwriteError: PropTypes.bool
}

export default ValidatedTextField;