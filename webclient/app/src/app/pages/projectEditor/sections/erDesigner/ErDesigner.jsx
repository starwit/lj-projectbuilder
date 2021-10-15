import React, {useState} from "react";
import {
    Button,
    Card,
    Drawer,
    Fab,
    Grid,
    IconButton,
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableRow,
    Typography
} from "@mui/material";
import {Add, Code, Delete, Edit} from "@mui/icons-material";
import {docco} from 'react-syntax-highlighter/dist/esm/styles/hljs';
import SyntaxHighlighter from 'react-syntax-highlighter';
import ErDesignerStyles from "./ErDesignerStyles";
import Draggable from "react-draggable";
import EntityEditor from "./entityEditor/EntityEditor";
import Statement from "../../../../commons/statement/Statement";

function ErDesigner() {

    const erDesignerStyles = ErDesignerStyles();
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useState(false);
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
                        "otherEntityName": "D2",
                        "otherEntityRelationshipName": "D2-f1-i",
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
        console.log("exData", exampleData)
    }

    function renderAttributes(entity) {

        return entity.fields.map((field, index) => {
            console.log("src", entity.name, field.name, "anchor_" + entity.name + "_" + field.name)
            return (
                <TableRow key={index}>
                    <TableCell>{field.name}</TableCell>
                    <TableCell>{field.dataType.name}</TableCell>
                    <TableCell id={"anchor_" + entity.name + "_" + field.name}/>
                </TableRow>
            )
        })
    }

    function renderFieldsTable(entity) {
        if (!entity.fields || entity.fields.length === 0) {
            return (
                <div className={erDesignerStyles.statementWrapper}>
                    <Statement message={"Keine Felder angelegt"}/>
                </div>
            )
        }
        return (
            <Table size={"small"}>
                <TableHead>
                    <TableRow className={erDesignerStyles.tableRow}>
                        <TableCell>Name</TableCell>
                        <TableCell>Datentyp</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {renderAttributes(entity)}
                </TableBody>
            </Table>
        )
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
            console.log("editing existing", updatedEntity)
            newExampleData.entities[foundEntityIndex] = updatedEntity
        } else {
            console.log("creating new one", updatedEntity)

            newExampleData.entities.push(updatedEntity);
        }

        setExampleData(newExampleData);
        setCurrentEntity(null)

    }

    function renderRelations() {
        const relationsList = [];
        exampleData.entities.forEach(entity => {
            if (entity.relationships) {
                return entity?.relationships.forEach(relationship => {
                    relationship.name = entity.name
                    relationsList.push(relationship)
                })
            }
        })
        const svgList = relationsList.map(parsedRelation => {
            const elementSource = document
                .getElementById('anchor_' + parsedRelation.name + '_' + parsedRelation.relationshipName)
                ?.getBoundingClientRect();
            const elementTarget = document
                .getElementById('anchor_' + parsedRelation.otherEntityName + '_' + parsedRelation.otherEntityRelationshipName)
                ?.getBoundingClientRect();
            if (elementTarget && elementSource) {
                console.log("element", elementSource, elementTarget)
                return (
                    <svg height={"100%"} width={"100%"}>
                        <line x1={elementSource.x} y1={elementSource.y} x2={elementTarget.x} y2={elementTarget.y}
                              stroke="black"/>
                    </svg>
                )
            }
        })
        console.log(svgList);
        return svgList;

    }

    function renderTitle(name) {
        let value = <Typography variant={"h6"} color={"text.secondary"}>Neue Entität</Typography>
        if (name) {
            value = <Typography variant={"h6"}>{name}</Typography>
        }
        return value;
    }

    function renderEntities() {
        return exampleData.entities.map(entity => {
            return (
                <Draggable bounds="parent" axis={"both"}>
                    <Card className={`${erDesignerStyles.entityCard} handle`}>
                        <Grid container>
                            <Grid item sm={8}>
                                {renderTitle(entity.name)}
                            </Grid>
                            <Grid item sm={2}>
                                <IconButton onClick={() => setCurrentEntity(entity)}><Edit
                                    fontSize={"small"}/></IconButton>
                            </Grid>
                            <Grid item sm={2}>
                                <IconButton onClick={() => deleteEntity(entity.id)}><Delete
                                    fontSize={"small"}/></IconButton>
                            </Grid>
                        </Grid>
                        {renderFieldsTable(entity)}
                    </Card>
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
            <EntityEditor entityId={currentEntity?.id} onClose={() => setCurrentEntity(null)}
                          handleSave={(data) => updateEntity(data)} entities={exampleData.entities}/>
        </>
    )
}

export default ErDesigner;