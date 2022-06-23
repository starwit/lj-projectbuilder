import {produce} from "immer";

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

function updateEnumInList(enums, enumDef, index) {
    return produce(enums, draft => {
        draft[index] = enumDef;
    });
}

export {
    enumDefault,
    updateEnumInList,
    updateEnumPosition
};
