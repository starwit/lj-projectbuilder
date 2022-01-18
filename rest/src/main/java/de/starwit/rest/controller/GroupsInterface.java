package de.starwit.rest.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;

import de.starwit.dto.UpdateGroupsDto;
import net.minidev.json.JSONArray;

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
        List<String> groups = (List<String>)accessToken.getOtherClaims().get("groups");
        
        return groups;
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
