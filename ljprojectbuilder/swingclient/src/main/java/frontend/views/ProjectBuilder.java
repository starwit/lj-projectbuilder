package frontend.views;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import frontend.beans.GeneratorSetupBean;
import frontend.beans.ProjectSetupBean;

public class ProjectBuilder {
	
	static final Logger LOG = Logger.getLogger(ProjectBuilder.class);

	private final frontend.beans.ProjectSetupBean projectSetup = new ProjectSetupBean();
	private final frontend.beans.GeneratorSetupBean generatorSetup = new GeneratorSetupBean();
	private JFrame frmProjectSetup;
	private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private GeneratorJPanel generatorPanel;
	private ProjectBuilderJPanel projectPanel;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjectBuilder window = new ProjectBuilder();
					window.frmProjectSetup.setVisible(true);
				} catch (Exception e) {
					LOG.error("Error creating project builder. ", e);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProjectBuilder() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProjectSetup = new JFrame();
		frmProjectSetup.setIconImage(Toolkit.getDefaultToolkit().getImage(ProjectBuilder.class.getResource("/img/projectbuilder.png")));
		frmProjectSetup.setTitle("Lirejarp Project Setup");
		frmProjectSetup.setBackground(SystemColor.inactiveCaptionBorder);
		frmProjectSetup.setBounds(100, 100, 656, 488);
		frmProjectSetup.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		frmProjectSetup.getContentPane().setLayout(gridBagLayout);
		
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		frmProjectSetup.getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		projectPanel = new ProjectBuilderJPanel(projectSetup);
		GridBagLayout gridBagLayout_1 = (GridBagLayout) projectPanel.getLayout();
		gridBagLayout_1.columnWidths = new int[]{0, 483, 0};
		gridBagLayout_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0};
		gridBagLayout_1.rowHeights = new int[]{0, 0, 0, 138, 0};
		tabbedPane.addTab("Project Setup", null, projectPanel, null);
		projectPanel.setBackground(SystemColor.inactiveCaptionBorder);
		
		generatorPanel = new GeneratorJPanel(generatorSetup);
		generatorPanel.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagLayout gridBagLayout_2 = (GridBagLayout) generatorPanel.getLayout();
		gridBagLayout_2.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout_2.columnWeights = new double[]{0.0, 1.0, 0.0};
		tabbedPane.addTab("Generator Setup", null, generatorPanel, null);
		
		tabbedPane.addChangeListener(new ChangeListener() {
		    public void stateChanged(ChangeEvent e) {
			    SwingUtilities.invokeLater(new Runnable() {
			        public void run() {
				    	if (tabbedPane.getSelectedIndex() == 1) {
				    		generatorSetup.setProjectName(projectSetup.getNewProjectName());
				    		generatorSetup.setProjectPath(projectSetup.getTargetPath());
			    		
				    		generatorPanel.newProjectNameBinding.unbind();
				    		generatorPanel.newProjectNameBinding.bind();
				    		generatorPanel.projectHomeBinding.unbind();
				    		generatorPanel.projectHomeBinding.bind();
				    	} else {
				    		projectSetup.setNewProjectName(generatorSetup.getProjectName());
				    		projectSetup.setTargetPath(generatorSetup.getProjectPath());
				    		projectPanel.newProjectNameBinding.unbind();
				    		projectPanel.newProjectNameBinding.bind();
				    		projectPanel.projectHomeBinding.unbind();
				    		projectPanel.projectHomeBinding.bind();
				    	}
			        }
			    });
		    }
		});

		projectPanel.setBackground(SystemColor.inactiveCaptionBorder);
	}

}
