import React, { useEffect, useState } from "react";
import { useTheme } from '@mui/material/styles';
import Box from '@mui/material/Box';
import OutlinedInput from '@mui/material/OutlinedInput';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Chip from '@mui/material/Chip';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

function getStyles(name, itemValue, theme) {
  if (!itemValue) {
    return null;
  } else {
    return {
      fontWeight:
        itemValue.indexOf(name) === -1
          ? theme.typography.fontWeightRegular
          : theme.typography.fontWeightMedium,
    };
  }
}

function MultipleSelectChip(props) {
  const { values, selected, handleExternalChange } = props;
  const theme = useTheme();
  const [itemValue, setItemValue] = useState(selected);

  const handleChange = (event) => {
    const {
      target: { value },
    } = event;
    setItemValue(
      // On autofill we get a stringified value.
      typeof value === 'string' ? value.split(',') : value,
    );
    handleExternalChange(typeof value === 'string' ? value.split(',') : value,);
  };

  useEffect(() => {
    setItemValue(selected);
  }, [selected]);

  return (
    <div>
      <FormControl sx={{ m: 1, width: 300 }}>
        <InputLabel id="demo-multiple-chip-label">Chip</InputLabel>
        <Select
          labelId="demo-multiple-chip-label"
          id="demo-multiple-chip"
          multiple
          value={itemValue}
          onChange={handleChange}
          input={<OutlinedInput id="select-multiple-chip" label="Chip" />}
          renderValue={() => (
            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
              {selected.map((value) => (
                <Chip key={value} label={value} />
              ))}
            </Box>
          )}
          MenuProps={MenuProps}
        >
          {values.map((item) => (
            <MenuItem
              key={item}
              value={item}
              style={getStyles(item, itemValue, theme)}
            >
              {item}
            </MenuItem>
          ))}
        </Select>
      </FormControl>
    </div>
  );
}

export default MultipleSelectChip;
