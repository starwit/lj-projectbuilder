const newEntity = {
    name: "",
    fields: [],
    relationships: [],
    isNewEntity: true,
    position: {
        positionX: 0,
        positionY: 0,
    },
};

const emptyEntity = {
    id: undefined,
    name: "none",
    fields: [],
    relationships: [],
    isNewEntity: false,
    position: {
        positionX: 0,
        positionY: 0,
    },
};

export { newEntity, emptyEntity };
