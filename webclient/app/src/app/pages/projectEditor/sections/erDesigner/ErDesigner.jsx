import React from "react";
import {Fab, Grid, Paper, TextField} from "@mui/material";
import {Add} from "@mui/icons-material";
import { docco } from 'react-syntax-highlighter/dist/esm/styles/hljs';
import SyntaxHighlighter from 'react-syntax-highlighter';


const exampleData = {
    "glossary": {
        "title": "example glossary",
        "GlossDiv": {
            "title": "S",
            "GlossList": {
                "GlossEntry": {
                    "ID": "SGML",
                    "SortAs": "SGML",
                    "GlossTerm": "Standard Generalized Markup Language",
                    "Acronym": "SGML",
                    "Abbrev": "ISO 8879:1986",
                    "GlossDef": {
                        "para": "A meta-markup language, used to create markup languages such as DocBook.",
                        "GlossSeeAlso": ["GML", "XML"]
                    },
                    "GlossSee": "markup"
                }
            }
        }
    }
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


    return (
        <>
            <Fab color="primary" aria-label="add" style={{position: "absolute", bottom: "5%", right: "2%"}}>
                <Add/>
            </Fab>
            <Grid container spacing={5}>
                <Grid item sm={4}>
                    <Paper elevation={5} style={{height: "100%"}}>
                        <SyntaxHighlighter language="json" style={docco} showLineNumbers >
                            {JSON.stringify(exampleData, null, 4)}
                        </SyntaxHighlighter>

                    </Paper>
                </Grid>
                <Grid item sm={6}>

                </Grid>
            </Grid>
        </>
    )

}

export default ErDesigner;