import React, {useEffect} from "react";
import {Button, Stack} from "@mui/material";
import {useTranslation} from "react-i18next";
import UpdateFormStyles from "./UpdateFormStyles";
import {isValid} from "../../modifiers/DefaultModifier";
import ValidatedTextField from "../inputFields/validatedTextField/ValidatedTextField";

function UpdateForm(props) {
    const {entity, fields, prefix, handleSubmit, handleChange} = props;
    const {t} = useTranslation();
    const updateFormStyles = UpdateFormStyles();
    const [hasFormError, setHasFormError] = React.useState(false);

    useEffect(() => {
        onEntityChange();
    }, [entity]);

    function onEntityChange() {
        setHasFormError(!isValid(fields, entity));
    }

    return (
            <form
                    className={updateFormStyles.container}
                    autoComplete="off"
                    onSubmit={handleSubmit}
            >
                <Stack spacing={2}>

                    {fields.map(field =>
                            <React.Fragment key={field.name}>
                                <ValidatedTextField
                                        inputProps={field.inputProps}
                                        key={field.name}
                                        id={"input-" + field.name}
                                        label={t(prefix + "." + field.name)}
                                        helperText={t(prefix + "." + field.name + ".hint")}
                                        name={field.name}
                                        type={field.type}
                                        value={entity[field.name] !== null ? entity[field.name] : ""}
                                        className={updateFormStyles.textField}
                                        onChange={handleChange}
                                        margin="normal"
                                        isCreate={!entity?.id}
                                        regex={field.regex}
                                />
                            </React.Fragment>
                    )}
                    <Button type="submit" variant="contained" color="secondary" disabled={hasFormError}>
                        {t("button.submit")}
                    </Button>
                </Stack>
            </form>
    );
}

export default UpdateForm;
