import React, {useMemo, useState, useEffect} from "react";
import {Code, Adjust} from "@mui/icons-material";
import {Button, Drawer} from "@mui/material";
import {docco} from "react-syntax-highlighter/dist/esm/styles/hljs";
import SyntaxHighlighter from "react-syntax-highlighter";
import Draggable from "react-draggable";
import {useTranslation} from "react-i18next";
import PropTypes from "prop-types";
import EntityDiagramStyles from "./EntityDiagramStyles";
import EntityDialog from "../entityDialog/EntityDialog";
import EntityCard from "../entityCard/EntityCard";
import Statement from "../../../../commons/statement/Statement";
import EntityRest from "../../../../services/EntityRest";
import MainTheme from "../../../../assets/themes/MainTheme";
import {renderRelations, updateRelationCoordinates} from "../HandleRelations";
import AddFabButton from "../../../../commons/addFabButton/AddFabButton";
import {updatePosition} from "../DefaultEntities";
import {useImmer} from "use-immer";

function EntityDiagram(props) {
    const {appId, editable, entities, dense, onChange} = props;

    const entityDiagramStyles = EntityDiagramStyles();
    const theme = new MainTheme();
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useImmer(false);
    const [coordinates, setCoordinates] = useState([]);
    const entityRest = useMemo(() => new EntityRest(), []);
    const [openEntityDialog, setOpenEntityDialog] = useState(false);

    const {t} = useTranslation();

    useEffect(() => {
        setCoordinates(updateRelationCoordinates(entities));
    }, [entities]);

    function addEntity() {
        setOpenEntityDialog(true);
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
        return entityRest.createEntityByApp(appId, entity);
    }

    function reloadEntities() {
        return entityRest.findAllEntitiesByApp(appId).then(response => {
            const newEntities = response.data;
            onChange(newEntities);
            return newEntities;
        });
    }

    function prepareUpdateEntity(updatedEntityInput) {
        const updatedEntity = {...updatedEntityInput};

        if (!editable) {
            return;
        }

        if (updatedEntity.isNewEntity) {
            delete updatedEntity.id;
            updatedEntity.isNewEntity = false;
        }

        if (currentEntity) {
            setCurrentEntity(updatedEntity);
        }
        let createEntity = updateEntity(updatedEntity);
        createEntity = createEntity.then(reloadEntities);
        return createEntity;
    }

    function savePosition(entity, index, draggableData) {
        const updatedEntity = updatePosition(entity, draggableData);
        const newEntities = [...entities];
        newEntities[index] = updatedEntity;
        onChange(newEntities);
        updateEntity(updatedEntity);
    }

    function renderEntities() {
        if (entities.length === 0) {
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
                    onStop={(update, draggableData) => savePosition(entity, index, draggableData)}
                    key={entity.id + index + ""}
                    defaultClassName={entityDiagramStyles.draggable}
                    position={entityCardPosition}
                    disabled={!editable}
                >
                    <div>
                        <EntityCard entity={entity} handleEdit={setCurrentEntity} handleDelete={deleteEntity}
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
            <AddFabButton onClick={addEntity}/>
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
        setCurrentEntity(null);
    }

    function centerEntities() {
        const newEntities = [...entities];
        newEntities.forEach((entity, index) => {
            const updatedEntity = updatePosition(entity, {x: index * 30 + 100, y: index * 10});
            newEntities[index] = updatedEntity;
            updateEntity(updatedEntity);
        });
        onChange(newEntities);
    }

    return (
        <>
            {renderAddEntityButton()}
            <div className={entityDiagramStyles.codeButtonWrapper}>
                <Button variant={"contained"} startIcon={<Code/>} onClick={openDrawer}>
                    {t("entity.code")}
                </Button>
            </div>
            <div className={entityDiagramStyles.centerButtonWrapper}>
                <Button variant={"outlined"} startIcon={<Adjust/>} onClick={centerEntities}>
                    {t("entity.center")}
                </Button>
            </div>
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
                {renderRelations(coordinates, theme)}
            </div>
            <EntityDialog
                entityId={currentEntity?.id}
                onClose={closeEntityDialog}
                handleSave={data => prepareUpdateEntity(data)}
                entities={entities}
                open={openEntityDialog}
            />
        </>
    );
}

EntityDiagram.propTypes = {
    entities: PropTypes.array,
    editable: PropTypes.bool,
    dense: PropTypes.bool
};

EntityDiagram.defaultProps = {
    editable: true,
    entities: [],
    dense: false
};

export default EntityDiagram;
