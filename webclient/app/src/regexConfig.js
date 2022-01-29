const RegexConfig = {

    entityTitle: /^[A-Z][a-zA-Z0-9]{1,100}$/,
    fieldName: /^[a-z][a-zA-Z0-9]{1,100}$/,
    applicationBaseName: /^[A-Za-z0-9]{1,100}$/,
    packageName: /^[A-Za-z0-9]{1,100}$/,

    appTemplateLocation: /^(?:git|ssh|https?|git@[-w.]+):(\/\/)?([a-zA-Z0-9./_-]*)(.git)$/,
    appTemplateBranch: /^([a-zA-Z0-9/_-]{0,100})$/,
    appTemplateAuthUser: /^[a-zA-Z0-9!@#$%^&()*./_-]{2,20}$/,
    appTemplateAuthPassword: /^[a-zA-Z0-9!@#$%^&()*./_-]{6,100}$/


}
export default RegexConfig;