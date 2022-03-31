import React, {Component} from "react";
import {withTranslation} from "react-i18next";
import {withStyles} from "@material-ui/core/styles";
import Button from "@material-ui/core/Button";
import AddUpdateFormStyles from "./AddUpdateFormStyles";
import {compose} from "recompose";
import TextField from "@material-ui/core/TextField";

class AddUpdateForm extends Component {
    render() {
        const {t, classes, entity, attributes, prefix, handleSubmit, handleChange} = this.props;

        return (
                <form
                        className={classes.container}
                        autoComplete="off"
                        onSubmit={handleSubmit}>

                    {attributes.map(attribute =>
                            <React.Fragment key={attribute.name}>
                                <TextField
                                        inputProps={attribute.inputProps}
                                        key={attribute.name}
                                        id={"input-" + attribute.name}
                                        label={t(prefix + "." + attribute.name)}
                                        name={attribute.name}
                                        type={attribute.type}
                                        value={entity[attribute.name] !== null ? entity[attribute.name] : ""}
                                        className={classes.textField}
                                        onChange={handleChange}
                                        margin="normal"
                                />
                                <br/>
                            </React.Fragment>
                    )}
                    <br/>
                    <Button type="submit" variant="contained" color="primary">
                        {t("button.submit")}
                    </Button>
                </form>
        );
    }
}

export default compose(withStyles(AddUpdateFormStyles), withTranslation())(AddUpdateForm);
