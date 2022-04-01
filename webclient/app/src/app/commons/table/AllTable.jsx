import React, {Component} from "react";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Table from "@material-ui/core/Table";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";

class AllTable extends Component {
    render() {
        const {entities, columns, selected, onRowClick} = this.props;

        return (
            <Table>
                <TableHead>
                    <TableRow>
                        {columns.map(column => <TableCell key={column.title}>{column.title}</TableCell>)}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {entities.map(entity =>
                        <TableRow key={entity.id} onClick={() => onRowClick(entity)}
                            selected={selected && entity.id === selected.id}>
                            {columns.map(column => <TableCell
                                key={entity.id + "." + column.field}>{entity[column.field]}</TableCell>)}
                        </TableRow>
                    )}
                </TableBody>
            </Table>
        );
    }
}

export default AllTable;
