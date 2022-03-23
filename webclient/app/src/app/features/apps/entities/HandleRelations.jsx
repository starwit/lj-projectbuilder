import { SteppedLineTo } from "react-lineto";

function updateRelationCoordinates(entities) {
    let coordinates = [];
    entities.forEach(entity => {
        if (entity.relationships) {
            entity?.relationships.forEach(relationship => {
                coordinates.push({
                    from: "anchor_" + entity.name,
                    to: "anchor_" + relationship.otherEntityName,
                });
            });
        }
    });
    return coordinates;
}

function renderRelations(coordinates, theme) {
    return coordinates?.map((coordinate, index) => {
        return (
            <SteppedLineTo
                from={coordinate.from}
                to={coordinate.to}
                borderColor={theme.palette.primary.main}
                borderWidth={theme.palette.line.width}
                key={coordinate.from + coordinate.to + index}
            />
        );
    });
}

export { updateRelationCoordinates, renderRelations };
