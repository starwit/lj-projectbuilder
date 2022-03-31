import React, { useMemo, useState } from "react";
import { Add, Code } from "@mui/icons-material";
import { Button, Drawer, Fab } from "@mui/material";
import { docco } from "react-syntax-highlighter/dist/esm/styles/hljs";
import SyntaxHighlighter from "react-syntax-highlighter";
import Draggable from "react-draggable";
import { useTranslation } from "react-i18next";
import PropTypes from "prop-types";
import EntityDiagramStyles from "./EntityDiagramStyles";
import EntityDialog from "../entityDialog/EntityDialog";
import EntityCard from "../entityCard/EntityCard";
import Statement from "../../../../commons/statement/Statement";
import EntityRest from "../../../../services/EntityRest";
import MainTheme from "../../../../assets/themes/MainTheme";
import { renderRelations } from "../HandleRelations";

function EntityDiagram(props) {
    const { editable, entities, coordinates, dense, reloadEntities, updateEntity } = props;

    const entityDiagramStyles = EntityDiagramStyles();
    const theme = new MainTheme();
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useState(false);
    const entityRest = useMemo(() => new EntityRest(), []);
    const [openEntityDialog, setOpenEntityDialog] = useState(false);

    const { t } = useTranslation();

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

    function prepareUpdateEntity(updatedEntityInput) {
        let updatedEntity = { ...updatedEntityInput };

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

        return updateEntity(updatedEntity);
    }

    function updatePosition(update, draggableData, entity) {
        if (!entity.position) {
            entity.position = {};
        }

        entity.position.positionX = draggableData.x;
        entity.position.positionY = draggableData.y;

        prepareUpdateEntity(entity)

    }

    function renderEntities() {
        if (entities.length === 0) {
            return <Statement message={t("app.entities.empty")} />;
        }
        return entities.map((entity, index) => {
            const entityCardPosition = { x: 0, y: 0 };

            if (entity.position) {
                const { positionX, positionY } = entity.position;
                entityCardPosition.x = positionX;
                entityCardPosition.y = positionY;
            }

            return (
                <Draggable
                    axis={"both"}
                    onStop={(update, draggableData) => updatePosition(update, draggableData, entity)}
                    key={entity.id + index + ""}
                    defaultClassName={entityDiagramStyles.draggable}
                    defaultPosition={entityCardPosition}
                    disabled={!editable}
                >
                    <div>
                        <EntityCard entity={entity} handleEdit={setCurrentEntity} handleDelete={deleteEntity} editable={editable} />
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
            <div className={entityDiagramStyles.addFab}>
                <Fab color="primary" aria-label="add" onClick={addEntity}>
                    <Add />
                </Fab>
            </div>
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

    return (
        <>
            {renderAddEntityButton()}
            <div className={entityDiagramStyles.codeButtonWrapper}>
                <Button variant={"contained"} startIcon={<Code />} onClick={openDrawer}>
                    {t("entity.code")}
                </Button>
            </div>
            <React.Fragment key={"left"}>
                <Drawer anchor={"left"} open={drawerOpen} onClose={closeDrawer} className={entityDiagramStyles.drawer}>
                    <SyntaxHighlighter
                        language="json"
                        style={docco}
                        showLineNumbers
                        customStyle={{
                            lineHeight: "1.5",
                            fontSize: ".75em",
                        }}
                        codeTagProps={{
                            className: entityDiagramStyles.syntaxHighlighterCodeTag,
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
                handleSave={(data) => prepareUpdateEntity(data)}
                entities={entities}
                open={openEntityDialog}
            />
        </>
    );
}

EntityDiagram.propTypes = {
    entities: PropTypes.array,
    editable: PropTypes.bool,
    dense: PropTypes.bool,
};

EntityDiagram.defaultProps = {
    editable: true,
    entities: [],
    dense: false,
};

export default EntityDiagram;
