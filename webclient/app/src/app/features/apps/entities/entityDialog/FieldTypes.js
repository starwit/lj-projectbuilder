const FieldTypes = [
    {
        name: "String",
        allowMin: true,
        allowMax: true,
        usesLengthLimit: true,
        allowPattern: true
    },
    {
        name: "Integer",
        allowMin: true,
        allowMax: true
    },
    {
        name: "Long",
        allowMin: true,
        allowMax: true
    },
    {
        name: "BigDecimal",
        allowMin: true,
        allowMax: true
    },
    {
        name: "Float",
        allowMin: true,
        allowMax: true
    },
    {
        name: "Double",
        allowMin: true,
        allowMax: true
    },
    {
        name: "Boolean"
    },
    {
        name: "Enum"
    },
    {
        name: "Date"
    },
    {
        name: "Time"
    },
    {
        name: "Timestamp"
    }
];

export default FieldTypes;
