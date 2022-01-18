package de.starwit.rest.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;

import de.starwit.dto.UpdateGroupsDto;
import de.starwit.persistence.converter.ListToStringConverter;

public interface GroupsInterface {

    default UpdateGroupsDto getGroups(Long appId, Principal principal, List<String> appGroups) {
        List<String> managedGroups = getGroups(principal);

       

        UpdateGroupsDto groupsToUpdate = new UpdateGroupsDto();
        groupsToUpdate.setId(appId);
        groupsToUpdate.setManagedGroups(managedGroups);
        groupsToUpdate.setGroupsToAdd(appGroups);

        return groupsToUpdate;
    }

    default List<String> getGroups(Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        String groups = accessToken.getOtherClaims().get("groups").toString();
        ListToStringConverter conv = new ListToStringConverter();
        return conv.convertToEntityAttribute(groups);
    }

    default List<String> identifyAssignedGroups(UpdateGroupsDto groupsToUpdated, List<String> assignedGroups) {
        List<String> groupsToAdd = groupsToUpdated.getGroupsToAdd();
        groupsToAdd = (groupsToAdd == null) ? new ArrayList<>() : groupsToAdd;
        assignedGroups = (assignedGroups == null) ? new ArrayList<>() : assignedGroups;

        if (groupsToUpdated.getManagedGroups() != null) {
            for (String managedGroup : groupsToUpdated.getManagedGroups()) {
                if (groupsToAdd.contains(managedGroup) && !assignedGroups.contains(managedGroup)) {
                    assignedGroups.add(managedGroup);
                } else if (!groupsToAdd.contains(managedGroup) && assignedGroups.contains(managedGroup)) {
                    assignedGroups.remove(managedGroup);
                }
            }
        }
        return assignedGroups;
    }

}
