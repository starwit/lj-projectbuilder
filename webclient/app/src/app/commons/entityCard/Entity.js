interface EntityDataType {
    id?: number,
    name?: string,
    fields?: [],
    relationships?: [],
    isNewEntity: boolean,
    position?: {
        positionX: number,
        positionY: number
    }
}

const newEntity : EntityDataType = {
    name: "",
    fields: [],
    relationships: [],
    isNewEntity: true,
    position: {
        positionX: 0,
        positionY: 0
    }
};

const nullEntity : EntityDataType = {
    id: undefined,
    name: "none",
    fields: [],
    relationships: [],
    isNewEntity: false,
    position: {
        positionX: 0,
        positionY: 0
    }
}

export {newEntity, nullEntity};