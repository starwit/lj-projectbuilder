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

function ErDesigner() {

    const erDesignerStyles = ErDesignerStyles();
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useState(false);
    const [exampleData, setExampleData] = useState({
        entities: [
            {
                id: 1,
                name: "Test",
                fields: [
                    {
                        name: "field1",
                        dataType: {
                            id: 2,
                            name: "integer",
                            allowMin: true,
                            allowMax: true
                        },
                        regex: "",
                        min: 0,
                        max: 20
                    },
                    {
                        name: "field2",
                        dataType: {
                            id: 2,
                            name: "integer",
                            allowMin: true,
                            allowMax: true
                        },
                        regex: "",
                        min: 0,
                        max: 20
                    },
                    {
                        name: "field3",
                        dataType: {
                            id: 2,
                            name: "integer",
                            allowMin: true,
                            allowMax: true
                        },
                        regex: "",
                        min: 0,
                        max: 20
                    },
                ]
            }
        ]
    })


    function renderAttributes(entity) {
        return entity.fields.map((field, index) => {
            return (
                <TableRow key={index}>
                    <TableCell>{field.name}</TableCell>
                    <TableCell>{field.dataType.name}</TableCell>
                </TableRow>
            )
        })
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

    function renderEntities() {
        return exampleData.entities.map(entity => {
            return (
                <Draggable bounds="parent" axis={"both"}>
                    <Card className={`${erDesignerStyles.entityCard} handle`}>
                        <Grid container>
                            <Grid item sm={8}>
                                <Typography variant={"h6"}>{entity.name}</Typography>
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
                    </Card>
                </Draggable>
            )
        })
    }

    return (
        <>
            <Fab color="primary" aria-label="add" className={erDesignerStyles.addFab}>
                <Add/>
            </Fab>
            <Button style={{zIndex: 5}} variant={"contained"} startIcon={<Code/>} onClick={openDrawer}>
                Code
            </Button>
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
                        {JSON.stringify(exampleData, null, 4)}
                    </SyntaxHighlighter>
                </Drawer>
            </React.Fragment>
            <div className={erDesignerStyles.draggableWrapper}>
                {renderEntities()}
            </div>
            <EntityEditor entityId={currentEntity?.id} onClose={() => setCurrentEntity(null)}
                          handleSave={(data) => updateEntity(data)} entities={exampleData.entities}/>
        </>
    )
}

export default ErDesigner;