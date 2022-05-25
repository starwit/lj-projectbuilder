const enumDefault = {
    name: "",
    value: "",
    id: undefined
};

function updateEnumPosition(enumDef, draggableData) {
    return produce(enumDef, draft => {
        draft.position = {};
        draft.position.positionX = draggableData.x;
        draft.position.positionY = draggableData.y;
    });
}

function updateEnumPositionInList(enums, enumDef, index, draggableData) {
    return produce(enums, draft => {
        draft[index] = updateEnumPosition(enumDef, draggableData);
    });
}

export {
    enumDefault,
    updateEnumPositionInList
};
