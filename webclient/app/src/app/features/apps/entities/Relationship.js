const RelationshipType = {
    oneToOne: "one-to-one",
    oneToMany: "one-to-many",
    ManyToOne: "many-to-one",
    manyToMany: "many-to-many"
};

const defaultRelationship = {
    relationshipType: RelationshipType.oneToMany,
    ownerSide: true
};

export {RelationshipType, defaultRelationship};

