package de.starwit.rest.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;

import de.starwit.dto.UpdateGroupsDto;

public interface GroupsInterface {

    default List<String> getGroups(Principal principal) {
        KeycloakAuthenticationToken keycloakAuthenticationToken = (KeycloakAuthenticationToken) principal;
        AccessToken accessToken = keycloakAuthenticationToken.getAccount().getKeycloakSecurityContext().getToken();
        List<String> groups = (List<String>)accessToken.getOtherClaims().get("groups");
        
        return groups;
    }

    default List<String> identifyAssignedGroups(List<String> groupsToAssign, List<String> assignedGroups, List<String> userGroups) {
        groupsToAssign = (groupsToAssign == null) ? new ArrayList<>() : groupsToAssign;
        assignedGroups = (assignedGroups == null) ? new ArrayList<>() : assignedGroups;

        if (userGroups != null) {
            for (String userGroup : userGroups) {
                if (groupsToAssign.contains(userGroup) && !assignedGroups.contains(userGroup)) {
                    assignedGroups.add(userGroup);
                } else if (!groupsToAssign.contains(userGroup) && assignedGroups.contains(userGroup)) {
                    assignedGroups.remove(userGroup);
                }
            }
        }
        return assignedGroups;
    }

}
