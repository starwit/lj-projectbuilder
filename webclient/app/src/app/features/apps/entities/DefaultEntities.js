import {produce} from "immer";

const newEntity = {
    name: "",
    fields: [],
    relationships: [],
    isNewEntity: true,
    position: {
        positionX: 0,
        positionY: 0
    }
};

const emptyEntity = {
    id: undefined,
    name: "none",
    fields: [],
    relationships: [],
    isNewEntity: false,
    position: {
        positionX: 0,
        positionY: 0
    }
};

function updatePosition(entity, draggableData) {
    const updatedEntity = {...entity};
    updatedEntity.position = {};
    updatedEntity.position.positionX = draggableData.x;
    updatedEntity.position.positionY = draggableData.y;
    return updatedEntity;
};

// // example compl

export {newEntity, emptyEntity, updatePosition};
