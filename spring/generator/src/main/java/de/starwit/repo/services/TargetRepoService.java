package de.starwit.repo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

@Local
@Stateless(name = "TargetRepoService")
public class TargetRepoService {
	
	private RepoServerData repoServerData;

    public static void main(String[] args) {
        //String targetUrl = "https://devstack.vwgroup.com/bitbucket/rest/api/1.0/projects/SPL/repos";
        //String repoURL = "https://devstack.vwgroup.com/bitbucket/rest/api/1.0/users/vwtdzwz/repos";
    }
    
    private Invocation.Builder buildRequester(RepoServerData repoServerData) {
    	
    	Client client = ClientBuilder.newClient();
        final WebTarget webTarget = client.target(repoServerData.getRepoRequestURL());
        final HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().build();
        webTarget.register(feature);
        
        final Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
        invocationBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME, repoServerData.getUsername());
        invocationBuilder.property(HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD, repoServerData.getPassword());
        return invocationBuilder;
    }
    
    public List<RepoData> listRepos() {
        Invocation.Builder invocationBuilder = buildRequester(repoServerData);
        Response response = invocationBuilder.get();   
        String output = response.readEntity(String.class);
        prettyPrintJSON(output);
        return deserializeRepoList(output);
    }
    
    private List<RepoData> deserializeRepoList(String jsonData) {
    	List<RepoData> repoDatas = new ArrayList<>();
    	try {
			JsonNode jsonNode = new ObjectMapper().readTree(jsonData);
			if(jsonNode.get("errors") != null) {
				return null;
			}
			
			JsonNode values = jsonNode.get("values");
			if(values.isArray()) {
				for(final JsonNode repoNode : values) {
					RepoData repoData = new RepoData();
					repoData.setName(repoNode.get("name").asText());
					repoData.setPublic(repoNode.get("public").asBoolean());
					JsonNode cloneUrls = repoNode.get("links").get("clone");
					for(final JsonNode urls : cloneUrls) {
						if("ssh".equals(urls.get("name").asText())) {
							repoData.setSshCloneURL(urls.get("href").asText());
						} else {
							repoData.setHttpCloneURL(urls.get("href").asText());
						}
					}
					repoDatas.add(repoData);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return repoDatas;
    }

    public boolean createTargetRepo() {
    	Invocation.Builder invocationBuilder = buildRequester(repoServerData);
    	String repoData = "{\"name\":\""+ repoServerData.getRepoName()  + "\"}";

        String postResponse = invocationBuilder.post(Entity.entity(repoData, MediaType.APPLICATION_JSON), String.class);

        prettyPrintJSON(postResponse);
    	
        return false;
    }

    private void prettyPrintJSON(String uglyJSON) {
    	String prettyJsonString = uglyJSON;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        try {
        	JsonElement je = jp.parse(uglyJSON);
        	prettyJsonString = gson.toJson(je);
        } catch (JsonSyntaxException ex) {
        	System.out.println("Failed to parse JSON " + ex.getMessage());
        }
        System.out.println(prettyJsonString);
    }
    
    public boolean initialCheckin() {
        return false;
    }

	public RepoServerData getRepoServerData() {
		return repoServerData;
	}

	public void setRepoServerData(RepoServerData repoServerData) {
		this.repoServerData = repoServerData;
	}
    
}
