package frontend.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;

import frontend.beans.ProjectSetupBean;
import logic.ProjectSetupService;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Font;

public class ProjectBuilderJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BindingGroup m_bindingGroup;
	private frontend.beans.ProjectSetupBean projectSetup;
	private JTextField currentProjectNameJTextField;
	private JTextField newProjectNameJTextField;
	private JTextField projectPathJTextField;
	private JFileChooser fileChooser;
	private JButton button;
	private JPanel panel;
	private JButton btnOk;
	private JButton btnAbbrechen;
	private ProjectSetupService projectSetupService = new ProjectSetupService();
	
	protected AutoBinding<ProjectSetupBean, String, JTextField, String> newProjectNameBinding;
	protected AutoBinding<ProjectSetupBean, String, JTextField, String> projectHomeBinding;
	protected AutoBinding<ProjectSetupBean, String, JTextField, String> targetPathBinding;
	
	Logger LOG = Logger.getLogger(ProjectBuilderJPanel.class);
	private JPanel panel_1;
	private JLabel lblTargetDirectory;
	private JTextField tragetPathJTextField;
	private JButton button_1;

	public ProjectBuilderJPanel(frontend.beans.ProjectSetupBean newProjectSetup) {
		this();
		setProjectSetup(newProjectSetup);
	}
	

	private ProjectBuilderJPanel() {
		setBackground(SystemColor.inactiveCaptionBorder);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 100, 355, 58, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 33, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 1.0E-4 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 1.0E-4 };
		setLayout(gridBagLayout);

		JLabel packageNameLabel = new JLabel("Current Name:");
		GridBagConstraints labelGbc_0 = new GridBagConstraints();
		labelGbc_0.anchor = GridBagConstraints.EAST;
		labelGbc_0.insets = new Insets(5, 5, 5, 5);
		labelGbc_0.gridx = 0;
		labelGbc_0.gridy = 0;
		add(packageNameLabel, labelGbc_0);

		currentProjectNameJTextField = new JTextField();
		GridBagConstraints componentGbc_0 = new GridBagConstraints();
		componentGbc_0.insets = new Insets(5, 0, 5, 5);
		componentGbc_0.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_0.gridx = 1;
		componentGbc_0.gridy = 0;
		add(currentProjectNameJTextField, componentGbc_0);

		JLabel projectNameLabel = new JLabel("App Name:");
		GridBagConstraints labelGbc_1 = new GridBagConstraints();
		labelGbc_1.anchor = GridBagConstraints.EAST;
		labelGbc_1.insets = new Insets(5, 5, 5, 5);
		labelGbc_1.gridx = 0;
		labelGbc_1.gridy = 1;
		add(projectNameLabel, labelGbc_1);

		newProjectNameJTextField = new JTextField();
		GridBagConstraints componentGbc_1 = new GridBagConstraints();
		componentGbc_1.insets = new Insets(5, 0, 5, 5);
		componentGbc_1.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_1.gridx = 1;
		componentGbc_1.gridy = 1;
		add(newProjectNameJTextField, componentGbc_1);

		JLabel projectPathLabel = new JLabel("Source Dir:");
		GridBagConstraints labelGbc_2 = new GridBagConstraints();
		labelGbc_2.anchor = GridBagConstraints.EAST;
		labelGbc_2.insets = new Insets(5, 5, 5, 5);
		labelGbc_2.gridx = 0;
		labelGbc_2.gridy = 2;
		add(projectPathLabel, labelGbc_2);

		projectPathJTextField = new JTextField();
		GridBagConstraints componentGbc_2 = new GridBagConstraints();
		componentGbc_2.anchor = GridBagConstraints.NORTH;
		componentGbc_2.insets = new Insets(5, 0, 5, 5);
		componentGbc_2.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_2.gridx = 1;
		componentGbc_2.gridy = 2;
		add(projectPathJTextField, componentGbc_2);
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setDialogTitle("Select Directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.EAST;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.VERTICAL;
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 2;
		add(panel_1, gbc_panel_1);
		
		button = new JButton("");
		button.setFont(new Font("Tahoma", Font.PLAIN, 8));
		button.setSelectedIcon(null);
		button.setIcon(new ImageIcon(ProjectBuilderJPanel.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		button.setBackground(UIManager.getColor("Button.background"));
		panel_1.add(button);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile(projectPathJTextField);
			}
		});
		
		lblTargetDirectory = new JLabel("Target Dir:");
		GridBagConstraints gbc_lblTargetDirectory = new GridBagConstraints();
		gbc_lblTargetDirectory.anchor = GridBagConstraints.EAST;
		gbc_lblTargetDirectory.insets = new Insets(0, 0, 5, 5);
		gbc_lblTargetDirectory.gridx = 0;
		gbc_lblTargetDirectory.gridy = 3;
		add(lblTargetDirectory, gbc_lblTargetDirectory);
		
		tragetPathJTextField = new JTextField();
		GridBagConstraints gbc_tragetPathJTextField = new GridBagConstraints();
		gbc_tragetPathJTextField.insets = new Insets(0, 0, 5, 5);
		gbc_tragetPathJTextField.fill = GridBagConstraints.HORIZONTAL;
		gbc_tragetPathJTextField.gridx = 1;
		gbc_tragetPathJTextField.gridy = 3;
		add(tragetPathJTextField, gbc_tragetPathJTextField);
		
		button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile(tragetPathJTextField);
			}
		});
		button_1.setIcon(new ImageIcon(ProjectBuilderJPanel.class.getResource("/javax/swing/plaf/metal/icons/ocean/directory.gif")));
		button_1.setToolTipText("Browse");
		button_1.setFont(new Font("Dialog", Font.PLAIN, 8));
		button_1.setBackground(UIManager.getColor("Button.background"));
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 0);
		gbc_button_1.gridx = 2;
		gbc_button_1.gridy = 3;
		add(button_1, gbc_button_1);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_panel.gridwidth = 2;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 4;
		add(panel, gbc_panel);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m_bindingGroup.bind();
				projectSetupService.copyProjectTemplate(projectSetup);
				projectSetupService.renameAll(projectSetup);
			}
		});
		panel.add(btnOk);
		
		btnAbbrechen = new JButton("Abbrechen");
		btnAbbrechen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(btnAbbrechen);

		if (projectSetup != null) {
			m_bindingGroup = initDataBindings();
		}
	}

	public frontend.beans.ProjectSetupBean getProjectSetup() {
		return projectSetup;
	}

	public void setProjectSetup(frontend.beans.ProjectSetupBean newProjectSetup) {
		setProjectSetup(newProjectSetup, true);
	}

	public void setProjectSetup(frontend.beans.ProjectSetupBean newProjectSetup, boolean update) {
		projectSetup = newProjectSetup;
		if (update) {
			if (m_bindingGroup != null) {
				m_bindingGroup.unbind();
				m_bindingGroup = null;
			}
			if (projectSetup != null) {
				m_bindingGroup = initDataBindings();
			}
		}
	}
	
	private void chooseFile(JTextField jTextField) {
		fileChooser.setSelectedFile(new File(jTextField.getText()));
		int returnVal = fileChooser.showOpenDialog(jTextField);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	jTextField.setText(fileChooser.getSelectedFile().getAbsolutePath()); 
            try {
              // return the file path 
            } catch (Exception ex) {
            	LOG.error("Problem accessing file.");
            }
        } 
        else {
        	LOG.info("File access cancelled by user.");
        }
	}   
	
	public BindingGroup getBindingGroup() {
		return m_bindingGroup;
	}
	protected BindingGroup initDataBindings() {
		BeanProperty<ProjectSetupBean, String> packageNameProperty = BeanProperty.create("currentProjectName");
		BeanProperty<JTextField, String> textProperty = BeanProperty.create("text");
		AutoBinding<ProjectSetupBean, String, JTextField, String> autoBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, projectSetup, packageNameProperty, currentProjectNameJTextField, textProperty);
		autoBinding.bind();
		//
		BeanProperty<ProjectSetupBean, String> projectNameProperty = BeanProperty.create("newProjectName");
		BeanProperty<JTextField, String> textProperty_1 = BeanProperty.create("text");
		newProjectNameBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, projectSetup, projectNameProperty, newProjectNameJTextField, textProperty_1);
		newProjectNameBinding.bind();
		//
		BeanProperty<ProjectSetupBean, String> projectPathProperty = BeanProperty.create("projectPath");
		BeanProperty<JTextField, String> textProperty_2 = BeanProperty.create("text");
		projectHomeBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, projectSetup, projectPathProperty, projectPathJTextField, textProperty_2);
		projectHomeBinding.bind();
		//
		BeanProperty<ProjectSetupBean, String> targetPathProperty = BeanProperty.create("targetPath");
		BeanProperty<JTextField, String> textProperty_3 = BeanProperty.create("text");
		targetPathBinding = Bindings.createAutoBinding(UpdateStrategy.READ_WRITE, projectSetup, targetPathProperty, tragetPathJTextField, textProperty_3);
		targetPathBinding.bind();
		//
		BindingGroup bindingGroup = new BindingGroup();
		//
		bindingGroup.addBinding(autoBinding);
		bindingGroup.addBinding(newProjectNameBinding);
		bindingGroup.addBinding(projectHomeBinding);
		bindingGroup.addBinding(targetPathBinding);
		return bindingGroup;
	}
}
