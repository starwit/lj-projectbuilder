import React, { useEffect, useState } from "react";
import { Box } from "@mui/material";
import OutlinedInput from "@mui/material/OutlinedInput";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import Chip from "@mui/material/Chip";
import MultipleSelectChipStyles from "./MultipleSelectChipStyles";

function MultipleSelectChip(props) {
    const { values, selected, handleExternalChange, label } = props;
    const [itemValue, setItemValue] = useState(selected);
    const multipleSelectChipStyles = MultipleSelectChipStyles();

    const handleChange = event => {
        const {
            target: { value },
        } = event;
        let itemValueNew = value;
        if (typeof value === "string") {
            itemValueNew = value.split(",");
        }
        setItemValue(itemValueNew);
        handleExternalChange(itemValueNew);
    };

    useEffect(() => {
        setItemValue(selected);
    }, [selected]);

    return (
        <FormControl fullWidth>
            <InputLabel id="multiple-chip-label">{label}</InputLabel>
            <Select
                labelId="multiple-chip-label"
                id="multiple-chip"
                multiple
                value={itemValue}
                onChange={handleChange}
                input={<OutlinedInput id="select-multiple-chip" label={label} />}
                renderValue={() => (
                    <Box className={multipleSelectChipStyles.selectBox}>
                        {selected.map(value => (
                            <Chip key={value} label={value} />
                        ))}
                    </Box>
                )}
            >
                {values.map(item => (
                    <MenuItem key={item} value={item}>
                        {item}
                    </MenuItem>
                ))}
            </Select>
        </FormControl>
    );
}

export default MultipleSelectChip;
