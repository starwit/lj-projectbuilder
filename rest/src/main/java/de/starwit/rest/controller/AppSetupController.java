 package de.starwit.rest.controller;

 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

import de.starwit.dto.GeneratorDto;
import de.starwit.generator.services.AppSetupService;
import de.starwit.persistence.exception.NotificationException;
import de.starwit.persistence.response.Response;
import de.starwit.persistence.response.ResponseCode;
import de.starwit.persistence.response.ResponseMetadata;

 /**
  * AppSetup RestController
  * Have a look at the RequestMapping!!!!!!
  */
@Deprecated
 //@RestController
 //@RequestMapping("${rest.base-path}/appsetup")
 public class AppSetupController {

	 final static Logger LOG = LoggerFactory.getLogger(AppSetupController.class);
	 
     //@Autowired
     private AppSetupService appSetupService;

//     @Autowired
//     private TargetRepoService targetRepoService;

     //@PostMapping(value = "/downloadapp")
     public Response<?> downloadApp(@RequestBody GeneratorDto dto) throws Exception {
      
       try {
    	 appSetupService.setupAndGenerateApp(dto);
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
         ResponseMetadata responseMetadata = new ResponseMetadata(ResponseCode.ERROR, "error.appsetup.downloadapp");
         response.setMetadata(responseMetadata);
         return response;
       }
     }
 }
