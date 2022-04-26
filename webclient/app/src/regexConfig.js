const RegexConfig = {

    entityTitle: /^[A-Z][a-zA-Z0-9]{1,100}$/,
    fieldName: /^[a-z][a-zA-Z0-9]{1,100}$/,
    applicationBaseName: /^[A-Za-z0-9]{1,100}$/,
    packageName: /^[A-Za-z0-9]{1,100}$/,

    appTemplateLocation: /^(?:git|ssh|https?|git@[-w.]+):(\/\/)?([a-zA-Z0-9./_-]*)(.git)$/,
    appTemplateBranch: /^([a-zA-Z0-9#/_\\-]{0,100})$/,

    relationship: /^([a-z][a-zA-Z0-9]{0,100})$/
};
export default RegexConfig;
