import React, {useEffect, useMemo, useState} from "react";
import {Adjust, Code} from "@mui/icons-material";
import {Button, Drawer} from "@mui/material";
import {docco} from "react-syntax-highlighter/dist/esm/styles/hljs";
import SyntaxHighlighter from "react-syntax-highlighter";
import Draggable from "react-draggable";
import {useTranslation} from "react-i18next";
import PropTypes from "prop-types";
import EntityDiagramStyles from "./EntityDiagramStyles";
import EntityDialog from "../entityDialog/EntityDialog";
import EntityCard from "../entityCard/EntityCard";
import EnumDialog from "../enumDialog/EnumDialog";
import EnumCard from "../enumCard/EnumCard";
import Statement from "../../../../commons/statement/Statement";
import EntityRest from "../../../../services/EntityRest";
import EnumRest from "../../../../services/EnumRest";
import {updateEnumPositionInList} from "../../../../modifiers/EnumModifier";
import AddFabButton from "../../../../commons/buttons/addFabButton/AddFabButton";
import {renderRelations, updateRelationCoordinates} from "../HandleRelations";
import {updatePosition} from "../DefaultEntities";
import {useTheme} from "@mui/styles";

function EntityDiagram(props) {
    const [appId, entities, enums, editable, dense, onChangeEntities, onChangeEnums] = props;

    const entityDiagramStyles = EntityDiagramStyles();
    const theme = new useTheme();
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [coordinates, setCoordinates] = useState([]);
    const entityRest = useMemo(() => new EntityRest(), []);
    const [openEntityDialog, setOpenEntityDialog] = useState(false);

    const [selectedEntityId, setSelectedEntityId] = useState(null);
    const [selectedEnum, setSelectedEnum] = useState(null);
    const enumRest = useMemo(() => new EnumRest(), []);
    const [openEnumDialog, setOpenEnumDialog] = useState(false);

    const {t} = useTranslation();

    useEffect(() => {
        setCoordinates(updateRelationCoordinates(entities));
    }, [entities]);

    function addEntity() {
        setOpenEntityDialog(true);
    }

    function addEnum() {
        setOpenEnumDialog(true);
    }

    function openDrawer() {
        setDrawerOpen(true);
    }

    function closeDrawer() {
        setDrawerOpen(false);
    }

    function deleteEntity(entityId) {
        if (!editable) {
            return;
        }
        return entityRest.delete(entityId)
            .then(reloadEntities);
    }

    function updateEntity(entity) {
        if (!editable) {
            return;
        }
        return entityRest.createEntityByApp(appId, entity);
    }

    function deleteEnum(enumId) {
        if (!editable) {
            return;
        }
        return enumRest.delete(entityId)
            .then(reloadEnums);
    }

    function updateEnum(enumDef) {
        if (!editable) {
            return;
        }
        return enumRest.createByApp(appId, enumDef).then(reloadEnums);
    }

    function reloadEntities() {
        if (!editable) {
            return;
        }
        return entityRest.findAllEntitiesByApp(appId).then(response => {
            const newEntities = response.data;
            onChangeEntities(newEntities);
            return newEntities;
        });
    }

    function reloadEnums() {
        if (!editable) {
            return;
        }
        return enumRest.findAllByApp(appId).then(response => {
            const newEnums = response.data;
            onChangeEnums(newEnums);
            return newEnums;
        });
    }

    function prepareUpdateEntity(updatedEntity) {
        if (!editable) {
            return;
        }
        return updateEntity(updatedEntity).then(reloadEntities);
    }

    function savePosition(entity, index, draggableData) {
        const updatedEntity = updatePosition(entity, draggableData);
        const newEntities = [...entities];
        newEntities[index] = updatedEntity;
        onChangeEntities(newEntities);
        updatePositionInDB(updatedEntity);
    }

    function renderEntities() {
        if (entities.length === 0 && enums.length === 0 ) {
            return <Statement message={t("app.entities.empty")}/>;
        }
        return entities.map((entity, index) => {
            const entityCardPosition = {x: 0, y: 0};

            if (entity.position) {
                const {positionX, positionY} = entity.position;
                entityCardPosition.x = positionX;
                entityCardPosition.y = positionY;
            }

            return (
                <Draggable
                    axis={"both"}
                    onStop={(update, draggableData) => {
                        savePosition(entity, index, draggableData);
                    }}
                    key={entity.id + index + ""}
                    defaultClassName={entityDiagramStyles.draggable}
                    position={entityCardPosition}
                    disabled={!editable}
                >
                    <div>
                        <EntityCard entity={entity} onEdit={setSelectedEntityId} handleDelete={deleteEntity}
                            editable={editable}/>
                    </div>
                </Draggable>
            );
        });
    }

    function renderEnums() {
        if (entities.length === 0 && enums.length === 0 ) {
            return;
        }
        return enums.map((enumDef, index) => {
            const enumDefPostiton = {x: 0, y: 0};

            if (enumDef.position) {
                const {positionX, positionY} = enumDef.position;
                enumDefPostiton.x = positionX;
                enumDefPostiton.y = positionY;
            }

            return (
                <Draggable
                    axis={"both"}
                    onStop={(update, draggableData) => {
                        onChangeEnums(updateEnumPositionInList(enums, enumDef, index, draggableData));
                    }}
                    key={enumDef.id + index + ""}
                    defaultClassName={entityDiagramStyles.draggable}
                    position={enumDefPostiton}
                    disabled={!editable}
                >
                    <div>
                        <EnumCard enumDef={enumDef} onEdit={setSelectedEnum} handleDelete={deleteEnum}
                            editable={editable}/>
                    </div>
                </Draggable>
            );
        });
    }

    function renderAddEntityButton() {
        if (!editable) {
            return;
        }

        return (
            <>
                <AddFabButton onClick={addEntity}/>
                <AddFabButton onClick={addEnum}/>
            </>
        );
    }

    function generateWrapper() {
        if (dense) {
            return entityDiagramStyles.draggableWrapperDense;
        }
        return entityDiagramStyles.draggableWrapper;
    }

    function closeEntityDialog() {
        setOpenEntityDialog(false);
        setSelectedEntityId(null);
    }

    function closeEnumDialog() {
        setOpenEnumDialog(false);
    }

    function centerEntities() {
        if (!editable) {
            return;
        }
        const newEntities = [...entities];
        newEntities.forEach((entity, index) => {
            const updatedEntity = updatePosition(entity, {x: index * 30 + 100, y: index * 10});
            newEntities[index] = updatedEntity;
            updatePositionInDB(updatedEntity);
        });
        onChangeEntities(newEntities);
    }

    function renderCenterButton() {
        if (!editable) {
            return;
        }

        return (
            <div className={entityDiagramStyles.centerButtonWrapper}>
                <Button variant={"outlined"} startIcon={<Adjust/>} onClick={centerEntities}>
                    {t("entity.center")}
                </Button>
            </div>
        );
    }

    return (
        <>
            {renderAddEntityButton()}
            <div className={entityDiagramStyles.codeButtonWrapper}>
                <Button variant={"contained"} startIcon={<Code/>} onClick={openDrawer}>
                    {t("entity.code")}
                </Button>
            </div>
            {renderCenterButton()}
            <React.Fragment key={"left"}>
                <Drawer anchor={"left"} open={drawerOpen} onClose={closeDrawer}
                    className={entityDiagramStyles.drawer}>
                    <SyntaxHighlighter
                        language="json"
                        style={docco}
                        showLineNumbers
                        customStyle={{
                            lineHeight: "1.5",
                            fontSize: ".75em"
                        }}
                        codeTagProps={{
                            className: entityDiagramStyles.syntaxHighlighterCodeTag
                        }}
                    >
                        {JSON.stringify(entities, null, 4)}
                    </SyntaxHighlighter>
                </Drawer>
            </React.Fragment>
            <div className={generateWrapper()}>
                {renderEntities()}
                {renderEnums()}
                {renderRelations(coordinates, theme)}
            </div>
            <EntityDialog
                entityId={selectedEntityId}
                onClose={closeEntityDialog}
                handleSave={data => prepareUpdateEntity(data)}
                entities={entities}
                open={openEntityDialog}
            />
            <EnumDialog
                enumDef={selectedEnum}
                onClose={closeEnumDialog}
                handleSave={data => updateEnum(data)}
                open={openEnumDialog}
            />
        </>
    );
}

EntityDiagram.propTypes = {
    appId: PropTypes.number,
    entities: PropTypes.array,
    enums: PropTypes.array,
    editable: PropTypes.bool,
    dense: PropTypes.bool
};

EntityDiagram.defaultProps = {
    editable: true,
    entities: [],
    enums: [],
    dense: false
};

export default EntityDiagram;
