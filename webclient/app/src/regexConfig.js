const RegexConfig = {

    entityTitle: /^[A-Z][a-zA-Z0-9]*$/,
    fieldName: /^[a-z][a-zA-Z0-9]*$/,
    applicationBaseName: /^[A-Za-z0-9]*$/,
    packageName: /^[A-Za-z0-9]*$/,

    appTemplateLocation: /^(?:git|ssh|https?|git@[-w.]+):(\/\/)?([a-zA-Z0-9./_-]*)(.git)(\/?|#[-dw._]+?)$/,
    appTemplateBranch: /^([a-zA-Z0-9/_-]*)$/,
    appTemplateAuthUser: /^[A-Za-z0-9]{2,20}$/,
    appTemplateAuthPassword: /^[a-zA-Z0-9!@#$%^&()*./_-]{6,30}$/


}
export default RegexConfig;