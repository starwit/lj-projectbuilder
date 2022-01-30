import React, {useMemo, useState} from "react";
import {Button, Drawer, Fab} from "@mui/material";
import {Add, CheckBoxOutlineBlank, Code} from "@mui/icons-material";
import {docco} from 'react-syntax-highlighter/dist/esm/styles/hljs';
import SyntaxHighlighter from 'react-syntax-highlighter';
import ErDesignerStyles from "./ErSectionStyles";
import Draggable from "react-draggable";
import EntityDialog from "../../../../commons/entityDialog/EntityDialog";
import EntityCard from "../../../../commons/entityCard/EntityCard";
import {SteppedLineTo} from "react-lineto";
import {useTranslation} from "react-i18next";
import PropTypes from "prop-types";
import Statement from "../../../../commons/statement/Statement";
import EntityRest from "../../../../services/EntityRest";
import MainTheme from "../../../../assets/themes/MainTheme";

function ErDesigner(props) {

    const {editable, entities, coordinates, updateCoordinates, handleUpdateEntities, dense, appId} = props;

    const erDesignerStyles = ErDesignerStyles();
    const theme = new MainTheme();
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useState(false);
    const entityRest = useMemo(() => new EntityRest(), []);

    const {t} = useTranslation();

    function addEntity() {
        const newEntities = [...entities];

        let newId = 1;
        if (newEntities.length > 0) {
            newId = newEntities[newEntities.length - 1].id + 1;
        }
        const newEntity = {
            id: newId,
            name: "",
            fields: [],
            relationships: [],
            isNewEntity: true,
            position: {
                positionX: 0,
                positionY: 0
            }
        };
        newEntities.push(newEntity);
        handleUpdateEntities(newEntities);
        setCurrentEntity(newEntity)
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

        //delete non-saved entity from ER-diagram
        let newEntities = [...entities];
        const locallyFoundIndex = newEntities.findIndex(entity => entity.id === entityId);
        if (newEntities[locallyFoundIndex].isNewEntity) {
            newEntities.splice(locallyFoundIndex, 1);
            handleUpdateEntities(newEntities);
        }

        return entityRest.delete(entityId)
            .then((response) => {
                return entityRest.findAllEntitiesByApp(appId)
            })
            .then(response => {
                handleUpdateEntities(response.data)
            })
    }

    function updateEntity(updatedEntityInput) {

        let updatedEntity = {...updatedEntityInput}

        if (!editable) {
            return;
        }

        if (updatedEntity.isNewEntity) {
            delete updatedEntity.id;
            updatedEntity.isNewEntity = false;
        }
        setCurrentEntity(updatedEntity)

        return entityRest.createEntityByApp(appId, updatedEntity)
            .then(() => {
                return entityRest.findAllEntitiesByApp(appId);
            })
            .then(response => {
                handleUpdateEntities(response.data)
            })


    }

    function updatePosition(update, draggableData, entity) {
        if (!entity.position) {
            entity.position = {};
        }

        entity.position.positionX = draggableData.x;
        entity.position.positionY = draggableData.y;

        entityRest.updateEntityByAppId(appId, entity)

        updateCoordinates(entities);
    }

    function renderRelations() {

        return coordinates.map((coordinate, index) => {
            return <SteppedLineTo from={coordinate.from} to={coordinate.to} borderColor={theme.palette.primary.main} borderWidth={theme.palette.line.width} />
        })

    }


    function renderEntities() {
        if (entities.length === 0) {
            return <Statement message={"No entities found"} icon={<CheckBoxOutlineBlank/>}/>
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
                    onStop={(update, draggableData) => updatePosition(update, draggableData, entity)}
                    key={entity.id + index + ""}
                    defaultClassName={erDesignerStyles.draggable}
                    defaultPosition={entityCardPosition}
                >
                    <div>
                        <EntityCard
                            entity={entity}
                            handleEdit={setCurrentEntity}
                            handleDelete={deleteEntity}
                            editable={editable}
                        />
                    </div>
                </Draggable>)
        })
    }

    function renderAddEntityButton() {

        if (!editable) {
            return;
        }

        return (
            <div className={erDesignerStyles.addFab}>
                <Fab color="primary" aria-label="add" onClick={addEntity}>
                    <Add/>
                </Fab>
            </div>
        )

    }

    function generateWrapper() {
        if (dense) {
            return erDesignerStyles.draggableWrapperDense
        }
        return erDesignerStyles.draggableWrapper
    }

    function closeEntityDialog() {
        setCurrentEntity(null)
    }

    return (<>
        {renderAddEntityButton()}
        <div className={erDesignerStyles.codeButtonWrapper}>
            <Button variant={"contained"} startIcon={<Code/>} onClick={openDrawer}>
                {t("entityDesigner.code")}
            </Button>
        </div>
        <React.Fragment key={"left"}>
            <Drawer
                anchor={"left"}
                open={drawerOpen}
                onClose={closeDrawer}
                className={erDesignerStyles.drawer}
            >
                <SyntaxHighlighter
                    language="json"
                    style={docco}
                    showLineNumbers
                    customStyle={{
                        lineHeight: "1.5", fontSize: ".75em"
                    }}
                    codeTagProps={{
                        className: erDesignerStyles.syntaxHighlighterCodeTag
                    }}
                >
                    {JSON.stringify(entities, null, 4)}
                </SyntaxHighlighter>
            </Drawer>
        </React.Fragment>
        <div className={generateWrapper()}>
            {renderEntities()}
            {renderRelations()}
        </div>
        <EntityDialog
            entityId={currentEntity?.id}
            onClose={closeEntityDialog}
            handleSave={(data) => updateEntity(data)}
            entities={entities}
        />
    </>)
}

ErDesigner.propTypes = {
    appId: PropTypes.number,
    handleUpdateEntities: PropTypes.func,
    entities: PropTypes.array,
    editable: PropTypes.bool,
    dense: PropTypes.bool
}

ErDesigner.defaultProps = {
    editable: true, entities: [], dense: false
};

export default ErDesigner;