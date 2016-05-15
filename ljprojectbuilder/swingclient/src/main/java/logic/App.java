package logic;

import frontend.beans.GeneratorSetupBean;

/**
 * Start generation via command line. 
 * @author anett
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	String projectName = args[0];
       	String domain = args[1];
       	startGeneration(projectName, domain);
    }
    
    private static void startGeneration(String projectName, String domain) {
    	
	   	GeneratorService generatorService = new GeneratorService();
	   	GeneratorSetupBean setupBean = new GeneratorSetupBean();
	   	
	   	setupBean.setGenerateEntity(true);
	   	setupBean.setGenerateService(true);
	   	setupBean.setGenerateRest(true);
	   	setupBean.setGenerateFrontend(true);
	   	setupBean.setGenerateTests(true);
	   	
	   	generatorService.generate(setupBean);
	    	
    }
}
