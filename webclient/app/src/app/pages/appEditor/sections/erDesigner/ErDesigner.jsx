import React, {useState} from "react";
import {Button, Drawer, Fab} from "@mui/material";
import {Add, Code} from "@mui/icons-material";
import {docco} from 'react-syntax-highlighter/dist/esm/styles/hljs';
import SyntaxHighlighter from 'react-syntax-highlighter';
import ErDesignerStyles from "./ErDesignerStyles";
import Draggable from "react-draggable";
import EntityEditor from "./entityEditor/EntityEditor";
import EntityCard from "../../../../commons/entityCard/EntityCard";
import {Line} from "react-lineto";

function ErDesigner() {

    const erDesignerStyles = ErDesignerStyles();

    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useState(false);
    const [coordinates, setCoordinates] = useState([]);
    const [exampleData, setExampleData] = useState({
        entities: [
            {
                "id": 1,
                "name": "D1",
                "fields": [
                    {
                        "name": "D1-f1-s",
                        "description": "",
                        "dataType": {
                            "id": 1,
                            "name": "string"
                        },
                        "pattern": "",
                        "min": "",
                        "max": "",
                        "mandatory": false
                    },
                    {
                        "name": "D1-f1-i",
                        "description": "",
                        "dataType": {
                            "id": 2,
                            "name": "integer",
                            "allowMin": true,
                            "allowMax": true
                        },
                        "pattern": "",
                        "min": "",
                        "max": "",
                        "mandatory": false
                    }
                ],
                "relationships": []
            },
            {
                "id": 2,
                "name": "D2",
                "fields": [
                    {
                        "name": "D2-f1-i",
                        "description": "",
                        "dataType": {
                            "id": 2,
                            "name": "integer",
                            "allowMin": true,
                            "allowMax": true
                        },
                        "pattern": "",
                        "min": "",
                        "max": "",
                        "mandatory": false
                    },
                    {
                        "name": "D2-f2-s",
                        "description": "",
                        "dataType": {
                            "id": 1,
                            "name": "string"
                        },
                        "pattern": "",
                        "min": "",
                        "max": "",
                        "mandatory": false
                    }
                ],
                "relationships": [
                    {
                        "relationshipType": "one-to-many",
                        "otherEntityName": "D1",
                        "otherEntityRelationshipName": "D1-f1-i",
                        "relationshipName": "D2-f1-i",
                        "name": "D2"
                    }
                ]
            }
        ]
    })

    function addEntity() {
        const newExampleData = {...exampleData};

        let newId = 1;
        if (exampleData.entities.length > 0) {
            newId = exampleData.entities[exampleData.entities.length - 1].id + 1;
        }
        const newEntity = {
            id: newId,
            name: "",
            fields: [],
            relationships: []
        };
        newExampleData.entities.push(
            newEntity
        )
        setExampleData(newExampleData)
        setCurrentEntity(newEntity)
    }


    function openDrawer() {
        setDrawerOpen(true);
    }

    function closeDrawer() {
        setDrawerOpen(false);
    }

    function deleteEntity(entityId) {
        const foundEntityIndex = exampleData.entities.findIndex(entity => entity.id === entityId)

        const newExampleData = {...exampleData}
        if (foundEntityIndex > -1) {
            newExampleData.entities.splice(foundEntityIndex, 1);
        }
        setExampleData(newExampleData);
    }

    function updateEntity(updatedEntity) {
        const foundEntityIndex = exampleData.entities.findIndex(entity => entity.id === updatedEntity.id);
        const newExampleData = {...exampleData};

        if (foundEntityIndex > -1) {
            newExampleData.entities[foundEntityIndex] = updatedEntity
        } else {
            newExampleData.entities.push(updatedEntity);
        }

        setExampleData(newExampleData);
        setCurrentEntity(null)

    }

    function updateCoordinates() {
        const relationsList = [];
        const coordinates = [];
        exampleData.entities.forEach(entity => {
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
        return exampleData.entities.map(entity => {
            return (
                <Draggable axis={"both"} onStop={updateCoordinates} style={{width: "20rem"}}>
                    <div>
                        <EntityCard entity={entity} handleEdit={setCurrentEntity} handleDelete={deleteEntity}/>
                    </div>
                </Draggable>
            )
        })
    }

    return (
        <>
            <div className={erDesignerStyles.addFab}>
                <Fab color="primary" aria-label="add" onClick={addEntity}>
                    <Add/>
                </Fab>
            </div>
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
                        {JSON.stringify(exampleData.entities, null, 4)}
                    </SyntaxHighlighter>
                </Drawer>
            </React.Fragment>
            <div className={erDesignerStyles.draggableWrapper}>
                {renderEntities()}
                {renderRelations()}
            </div>
            <EntityEditor
                entityId={currentEntity?.id}
                onClose={() => setCurrentEntity(null)}
                handleSave={(data) => updateEntity(data)}
                entities={exampleData.entities}
            />
        </>
    )
}

export default ErDesigner;