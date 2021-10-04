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


const exampleData = {
    entities: [
        {
            id: 1,
            name: "Test",
            attributes: [
                {
                    name: "field1",
                    dataType: "string",
                    regex: "",
                    min: 0,
                    max: 20
                },
                {
                    name: "field2",
                    dataType: "string",
                    regex: "",
                    min: 0,
                    max: 20
                },
                {
                    name: "field3",
                    dataType: "string",
                    regex: "",
                    min: 0,
                    max: 20
                },
            ]
        }
    ]
}

// https://stackoverflow.com/questions/30765163/pretty-printing-json-with-react
const PrettyPrintJson = React.memo(({data}) => {
    console.log(data)
    return (
        <div>
        <pre>
            {JSON.stringify(data, null, 2)}
        </pre>
        </div>
    )
});


function ErDesigner() {

    const erDesignerStyles = ErDesignerStyles();
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [currentEntity, setCurrentEntity] = useState(false);


    function renderAttributes(entity) {
        return entity.attributes.map((attribute, index) => {
            return (
                <TableRow key={index}>
                    <TableCell>{attribute.name}</TableCell>
                    <TableCell>{attribute.dataType}</TableCell>
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

    function renderEntities() {
        return exampleData.entities.map(entity => {
            return (
                <Card className={erDesignerStyles.entityCard}>
                    <Grid container>
                        <Grid item sm={8}>
                            <Typography variant={"h6"}>{entity.name}</Typography>
                        </Grid>
                        <Grid item sm={2}>
                            <IconButton onClick={() => setCurrentEntity(entity)}><Edit fontSize={"small"}/></IconButton>
                        </Grid>
                        <Grid item sm={2}>
                            <IconButton><Delete fontSize={"small"}/></IconButton>
                        </Grid>
                    </Grid>
                    <Table size={"small"}>
                        <TableHead>
                            <TableRow sx={{'&:last-child td, &:last-child th': {border: 0}}}>
                                <TableCell>Name</TableCell>
                                <TableCell>Datentyp</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {renderAttributes(entity)}
                        </TableBody>
                    </Table>
                </Card>
            )
        })
    }

    return (
        <>
            <Fab color="primary" aria-label="add" style={{position: "absolute", bottom: "5%", right: "2%"}}>
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
                    sx={{
                        width: "50vw",
                        flexShrink: 0,
                        '& .MuiDrawer-paper': {
                            width: "50vw",
                            boxSizing: 'border-box',
                        },
                    }}
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
            <div style={{height: "70vh"}}>
                <Draggable bounds="parent" axis={"both"}>
                    <div style={{width: "20rem"}}>
                        {renderEntities()}
                    </div>
                </Draggable>
            </div>
            <EntityEditor entityId={currentEntity.id} onClose={() => setCurrentEntity(null)}/>
        </>
    )
}

export default ErDesigner;