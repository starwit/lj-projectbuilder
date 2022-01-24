const RelationshipType = {
    oneToOne: "one-to-one",
    oneToMany: "one-to-many",
    ManyToOne: "many-to-one",
    manyToMany: "many-to-many"
}

interface RelationshipDataType {
    relationshipType: RelationshipType,
    otherEntityName?: string,
    relationshipName?: string,
    otherEntityRelationshipName?: string
}

const DefaultRelationship : RelationshipDataType = {
    relationshipType: RelationshipType.oneToMany,
}

export {RelationshipType, DefaultRelationship};

