import React, {useState, useEffect} from "react";
import {TextField} from "@mui/material";
import PropTypes from "prop-types";

function ValidatedTextField(props) {

    const {value, regex, isCreate} = props;
    const [error, setError] = useState(false);
    const [changed, setChanged] = useState(false);

    useEffect(() => {
        setError(!regex.test(value));
    }, [value, regex]);

    return (
        <TextField
            error={(!isCreate || changed) && error}
            value={value}
            onBlur={() => setChanged(true)}
            {...props}
        />
    )

}
ValidatedTextField.defaultProps = {
    value: "",
    isCreate: false
}

ValidatedTextField.propTypes = {
    helperText: PropTypes.string.isRequired,
    regex: PropTypes.any.isRequired,
    onChange: PropTypes.func.isRequired,
    value: PropTypes.string,
    isCreate: PropTypes.bool
}

export default ValidatedTextField;
