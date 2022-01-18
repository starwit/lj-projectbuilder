package de.starwit.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.persistence.entity.AbstractEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@XmlRootElement
public class UpdateGroupsDto extends AbstractEntity<Long> {
    
    private List<String> groupsToAdd;
    private List<String> managedGroups;

    public List<String> getGroupsToAdd() {
        return groupsToAdd;
    }
    public void setGroupsToAdd(List<String> groupsToAdd) {
        this.groupsToAdd = groupsToAdd;
    }
    public List<String> getManagedGroups() {
        return managedGroups;
    }
    public void setManagedGroups(List<String> managedGroups) {
        this.managedGroups = managedGroups;
    }   
}
