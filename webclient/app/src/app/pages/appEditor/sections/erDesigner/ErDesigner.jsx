import React, {useState} from "react";
import {Button, Drawer, Fab} from "@mui/material";
import {Add, CheckBoxOutlineBlank, Code} from "@mui/icons-material";
import {docco} from 'react-syntax-highlighter/dist/esm/styles/hljs';
import SyntaxHighlighter from 'react-syntax-highlighter';
import ErDesignerStyles from "./ErDesignerStyles";
import Draggable from "react-draggable";
import EntityEditor from "./entityEditor/EntityEditor";
import EntityCard from "../../../../commons/entityCard/EntityCard";
import {Line} from "react-lineto";
import PropTypes from "prop-types";
import Statement from "../../../../commons/statement/Statement";

function ErDesigner(props) {

    const {editable, entities, handleUpdateEntities, dense} = props;

    const erDesignerStyles = ErDesignerStyles();

    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useState(false);
    const [coordinates, setCoordinates] = useState([]);

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
            relationships: []
        };
        newEntities.push(
            newEntity
        )
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

        const foundEntityIndex = entities.findIndex(entity => entity.id === entityId)

        const newEntities = [...entities];
        if (foundEntityIndex > -1) {
            newEntities.splice(foundEntityIndex, 1);
        }
        handleUpdateEntities(newEntities)
    }

    function updateEntity(updatedEntity) {
        if (!editable) {
            return;
        }

        const foundEntityIndex = entities.findIndex(entity => entity.id === updatedEntity.id);
        const newEntities = [...entities];

        if (foundEntityIndex > -1) {
            newEntities[foundEntityIndex] = updatedEntity
        } else {
            newEntities.push(updatedEntity);
        }

        handleUpdateEntities(newEntities);
        setCurrentEntity(null)

    }

    function updateCoordinates() {
        const relationsList = [];
        const coordinates = [];
        entities.forEach(entity => {
            if (entity.relationships) {
                return entity?.relationships.forEach(relationship => {
                    relationship.name = entity.name
                    relationsList.push(relationship)
                })
            }
        })
        relationsList.forEach(parsedRelation => {
            let elementSource = getElement('anchor_' + parsedRelation.name + '_' + parsedRelation.relationshipName + '_r')
            let elementTarget = getElement('anchor_' + parsedRelation.otherEntityName + '_' + parsedRelation.otherEntityRelationshipName + '_l')

            if (elementSource?.x > elementTarget?.x) {
                elementSource = getElement('anchor_' + parsedRelation.name + '_' + parsedRelation.relationshipName + '_l')
                elementTarget = getElement('anchor_' + parsedRelation.otherEntityName + '_' + parsedRelation.otherEntityRelationshipName + '_r')
            }
            if (elementTarget && elementSource) {
                coordinates.push({
                    x0: elementSource.x,
                    y0: elementSource.y,
                    x1: elementTarget.x,
                    y1: elementTarget.y,
                })
            }
        })
        setCoordinates(coordinates)
    }

    function getElement(className) {
        const classes = document
            .getElementsByClassName(className)
        return classes[0]?.getBoundingClientRect();
    }

    function renderRelations() {

        return coordinates.map(coordinate => {
            return <Line x0={coordinate.x0} y0={coordinate.y0} x1={coordinate.x1} y1={coordinate.y1}/>
        })

    }


    function renderEntities() {
        if (entities.length === 0) {
            return <Statement message={"No entities found"} icon={<CheckBoxOutlineBlank/>}/>
        }
        return entities.map(entity => {
            return (
                <Draggable axis={"both"} onStop={updateCoordinates} style={{width: "20rem"}}>
                    <div>
                        <EntityCard
                            entity={entity}
                            handleEdit={setCurrentEntity}
                            handleDelete={deleteEntity}
                            editable={editable}
                        />
                    </div>
                </Draggable>
            )
        })
    }

    function renderAddFab() {

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

    return (
        <>
            {renderAddFab()}
            <div className={erDesignerStyles.codeButtonWrapper}>
                <Button variant={"contained"} startIcon={<Code/>} onClick={openDrawer}>
                    Code
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
                            lineHeight: "1.5",
                            fontSize: ".75em"
                        }}
                        codeTagProps={{
                            style: {
                                lineHeight: "inherit",
                                fontSize: "inherit"
                            }
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
            <EntityEditor
                entityId={currentEntity?.id}
                onClose={() => setCurrentEntity(null)}
                handleSave={(data) => updateEntity(data)}
                entities={entities}
            />
        </>
    )
}

ErDesigner.propTypes = {
    handleUpdateEntities: PropTypes.func,
    entities: PropTypes.array,
    editable: PropTypes.bool,
    dense: PropTypes.bool
}

ErDesigner.defaultProps = {
    editable: true,
    entities: [],
    dense: false
};

export default ErDesigner;