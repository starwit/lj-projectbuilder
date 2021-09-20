// class representing the AppTemplate entity
class AppTemplate {

    constructor(data) {
        this.id = data.id;
        this.name = data.name;
        this.templatefiles = data.templatefiles;
        this.description = data.description;
    }
}

export default AppTemplate;