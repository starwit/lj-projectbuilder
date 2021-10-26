import React from "react";
import {Input, TextField} from "@mui/material";

function GeneralSection() {

    return (
        <>
            <TextField label={"Name des Projekts"} fullWidth></TextField>
            <TextField label={"Beschreibung des Projekts"} fullWidth></TextField>
        </>
    )

}

export default GeneralSection;