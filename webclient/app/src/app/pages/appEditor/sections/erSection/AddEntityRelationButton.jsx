import * as React from 'react';
import Box from '@mui/material/Box';
import SpeedDial from '@mui/material/SpeedDial';
import SpeedDialIcon from '@mui/material/SpeedDialIcon';
import SpeedDialAction from '@mui/material/SpeedDialAction';
import { DomainAdd, Share } from '@mui/icons-material';

function AddEntityRelationButton(props) {
    const{ addEntity, addRelation } = props;
    const actions = [
        { icon: <DomainAdd />, name: 'Add Entity', onClick: addEntity },
        { icon: <Share />, name: 'Add Relation', onClick: addRelation },
    ];

    return (
        <Box sx={{ height: 320, transform: 'translateZ(0px)', flexGrow: 1 }}>
            <SpeedDial
                ariaLabel="SpeedDial basic example"
                sx={{ position: 'absolute', bottom: 16, right: 16 }}
                icon={<SpeedDialIcon />}
            >
                {actions.map((action) => (
                    <SpeedDialAction
                        key={action.name}
                        icon={action.icon}
                        tooltipTitle={action.name}
                        onClick={action.onClick}
                    />
                ))}
            </SpeedDial>
        </Box>
    );
}
export default AddEntityRelationButton;
