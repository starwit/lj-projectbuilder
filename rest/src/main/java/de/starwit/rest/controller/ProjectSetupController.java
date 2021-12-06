 package de.starwit.rest.controller;

 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.starwit.generator.dto.GeneratorDto;
import de.starwit.generator.services.ProjectSetupService;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.response.Response;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;

 /**
  * ProjectSetup RestController
  * Have a look at the RequestMapping!!!!!!
  */
 @RestController
 @RequestMapping("${rest.base-path}/projectsetup")
 public class ProjectSetupController {

	 final static Logger LOG = LoggerFactory.getLogger(ProjectSetupService.class);
	 
     @Autowired
     private ProjectSetupService projectSetupService;

//     @Autowired
//     private TargetRepoService targetRepoService;

     @PostMapping(value = "/downloadproject")
     public Response<?> downloadProject(@RequestBody GeneratorDto dto) throws Exception {
      
       try {
    	 projectSetupService.setupAndGenerateProject(dto);
         Response<Boolean> response = new Response<>(true);
         ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.OK, "generator.success");
         response.setMetadata(responseMetadata);
         return response;
       } catch (NotificationException nex) {
         LOG.error(nex.getMessage());
         Response<Boolean> response = new Response<>(false);
         response.setMetadata(nex.getResponseMetadata());
         return response;
       } catch (Exception ex) {
         LOG.error(ex.getMessage(), ex);
         Response<Boolean> response = new Response<>(false);
         ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.ERROR, "error.projectsetup.downloadproject");
         response.setMetadata(responseMetadata);
         return response;
       }
     }

//     @PostMapping(vlue = "/currentrepos")
//     public Response<List<RepoData>> listCurrentRepos(RepoServerData data) {
//       Response<List<RepoData>> response = new Response<>();
//      
//       if("".equals(data.getProjectName())) {
//         data.setProjectName(null);
//       }
//          
//       targetRepoService.setRepoServerData(data);
//       List<RepoData> repoData = targetRepoService.listRepos();
//       if (repoData == null) {
//         response.setMetadata(new ResponseMetadata(ResponseCode.ERROR, "generator.target.test.error"));
//       } else {
//         response.setMetadata(new ResponseMetadata(ResponseCode.OK, "generator.target.test.success"));
//         response.setResult(repoData);
//       }
//      
//       return response;
//     }

//     @PostMapping(value = "/createtargetrepo")
//     public Response<Boolean> createTargetRepo(RepoServerData data) {
//       boolean result = false;
//       targetRepoService.setRepoServerData(data);
//       result = targetRepoService.createTargetRepo();
//       Response<Boolean> response = new Response<>(result);
//       if(result) {
//         response.setMetadata(new ResponseMetadata(ResponseCode.OK, "generator.target.create.success"));
//       } else {
//         response.setMetadata(new ResponseMetadata(ResponseCode.ERROR, "generator.target.create.error"));
//       }
//      
//       return response;
//     }

 }
