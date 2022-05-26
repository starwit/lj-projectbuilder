import Schema from "@mui/icons-material/Schema";
import DataArray from "@mui/icons-material/DataArray";
import EntityDiagramButtonStyles from "./EntityDiagramButtonStyles";
import SpeedDial from "@mui/material/SpeedDial";
import SpeedDialAction from "@mui/material/SpeedDialAction";
import SpeedDialIcon from "@mui/material/SpeedDialIcon";
import {useTranslation} from "react-i18next";
import * as React from "react";

function EntityDiagramButton(props) {
    const {handleEntityEdit, handleEnumEdit} = props;
    const buttonStyle = EntityDiagramButtonStyles();
    const {t} = useTranslation();
    const actions = [
        {icon: <Schema />, name: t("entity.diagram.entity"), func: handleEntityEdit},
        {icon: <DataArray />, name: t("entity.diagram.enum"), func: handleEnumEdit}
    ];

    return (
        <div className={buttonStyle.fabWrapper}>
            <SpeedDial
                ariaLabel={t("entity.diagram.edit")}
                sx={{position: "absolute", bottom: 16, right: 16}}
                icon={<SpeedDialIcon />}
            >
                {actions.map(action => (
                    <SpeedDialAction
                        key={action.name}
                        icon={action.icon}
                        tooltipTitle={action.name}
                        tooltipOpen
                        onClick={action.func}
                    />
                ))}
            </SpeedDial>
        </div>
    );
}

export default EntityDiagramButton;
