import React, {useMemo, useState} from "react";
import {Button, Drawer, Fab} from "@mui/material";
import {Add, Code} from "@mui/icons-material";
import {docco} from 'react-syntax-highlighter/dist/esm/styles/hljs';
import SyntaxHighlighter from 'react-syntax-highlighter';
import ErDesignerStyles from "./ErSectionStyles";
import Draggable from "react-draggable";
import EntityDialog from "../../../../commons/entityDialog/EntityDialog";
import EntityCard from "../../../../commons/entityCard/EntityCard";
import {useTranslation} from "react-i18next";
import PropTypes from "prop-types";
import Statement from "../../../../commons/statement/Statement";
import EntityRest from "../../../../services/EntityRest";
import MainTheme from "../../../../assets/themes/MainTheme";
import { renderRelations } from "../../HandleRelations";

function ErDesigner(props) {

    const {editable, entities, coordinates, handleUpdateEntities, dense, appId} = props;

    const erDesignerStyles = ErDesignerStyles();
    const theme = new MainTheme();
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useState(false);
    const entityRest = useMemo(() => new EntityRest(), []);
    const [openEntityDialog, setOpenEntityDialog] = useState(false);

    const {t} = useTranslation();


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
            .then(() => {
                entityRest.findAllEntitiesByApp(appId).then((response) => {
                    handleUpdateEntities(response.data);
                })
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
        setCurrentEntity(updatedEntity);

        return entityRest.createEntityByApp(appId, updatedEntity);
    }

    function updatePosition(update, draggableData, entity) {
        if (!entity.position) {
            entity.position = {};
        }

        entity.position.positionX = draggableData.x;
        entity.position.positionY = draggableData.y;

        entityRest.updateEntityByAppId(appId, entity);

        handleUpdateEntities(entities);
    }

    function renderEntities() {
        if (entities.length === 0) {
            return <Statement message={"No entities found"}/>
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
                    disabled={!editable}
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
        setOpenEntityDialog(false);
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
            {renderRelations(coordinates, theme)}
        </div>
        <EntityDialog
            entityId={currentEntity?.id}
            onClose={closeEntityDialog}
            handleSave={(data) => updateEntity(data)}
            entities={entities}
            handleUpdateEntities={(updatedEntities) => handleUpdateEntities(updatedEntities)}
            open={openEntityDialog}
            appId={appId}
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