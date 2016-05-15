package frontend.views;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;

import org.apache.log4j.Logger;
import org.jdesktop.beansbinding.AutoBinding;
import org.jdesktop.beansbinding.BeanProperty;
import org.jdesktop.beansbinding.BindingGroup;
import org.jdesktop.beansbinding.Bindings;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.swingbinding.JTableBinding;
import org.jdesktop.swingbinding.SwingBindings;

import frontend.beans.DataType;
import frontend.beans.DomainAttributeBean;
import frontend.beans.GeneratorSetupBean;
import logic.GeneratorService;
import net.miginfocom.swing.MigLayout;

public class GeneratorJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private BindingGroup m_bindingGroup;
	private JTableBinding<DomainAttributeBean, List<DomainAttributeBean>, JTable> tableBinding;
	private frontend.beans.GeneratorSetupBean generatorSetupBean = new frontend.beans.GeneratorSetupBean();
	private JTextField projectNameJTextField;
	private JTextField projectPathJTextField;
	private JTextField templatePathJTextField;
	private JTextField domainNameJTextField;
	private MyTable table;
	private JPanel panel;
	private JButton btnOk;
	private JButton btnAbbrechen;
	private JButton btnRemoveAttibuteRow;
	private JPanel panel_1;
	private JButton btnAddAttributeRow;
	private final GeneratorService generatorService = new GeneratorService();
	private JPanel panel_2;
	private JButton button;
	private JFileChooser fileChooser;
	
	Logger LOG = Logger.getLogger(GeneratorJPanel.class);
	private JPanel panel_3;
	private JButton templatePathChooserButton;
	private JPanel generatorOptionPanel;
	private JCheckBox chckbxFrontend;
	private JCheckBox chckbxRestfulWebservices;
	private JCheckBox chckbxBackendservices;
	private JCheckBox chckbxTestdata;
	private JCheckBox chckbxEntity;
	private JComboBox<DataType> dataTypeCombobox;
	protected AutoBinding<frontend.beans.GeneratorSetupBean, java.lang.String, javax.swing.JTextField, java.lang.String> newProjectNameBinding;
	protected AutoBinding<frontend.beans.GeneratorSetupBean, java.lang.String, javax.swing.JTextField, java.lang.String> projectHomeBinding;

	public GeneratorJPanel(frontend.beans.GeneratorSetupBean newGeneratorSetupBean) {
		this();
		setGeneratorSetupBean(newGeneratorSetupBean);
	}

	public GeneratorJPanel() {
		setBackground(SystemColor.inactiveCaptionBorder);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 109, 351, 51 };
		gridBagLayout.rowHeights = new int[] { 0, 21, 19, 20, 0, 31, 63, 69, 51 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 1.0 };
		setLayout(gridBagLayout);
		
		fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new java.io.File("."));
		fileChooser.setDialogTitle("Select Directory");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
				JLabel projectNameLabel = new JLabel("Project Name:");
				GridBagConstraints labelGbc_0 = new GridBagConstraints();
				labelGbc_0.anchor = GridBagConstraints.EAST;
				labelGbc_0.insets = new Insets(5, 5, 5, 5);
				labelGbc_0.gridx = 0;
				labelGbc_0.gridy = 0;
				add(projectNameLabel, labelGbc_0);
		
				projectNameJTextField = new JTextField();
				GridBagConstraints componentGbc_0 = new GridBagConstraints();
				componentGbc_0.insets = new Insets(5, 0, 5, 5);
				componentGbc_0.fill = GridBagConstraints.HORIZONTAL;
				componentGbc_0.gridx = 1;
				componentGbc_0.gridy = 0;
				add(projectNameJTextField, componentGbc_0);

		JLabel projectPathLabel = new JLabel("Project Home:");
		GridBagConstraints labelGbc_1 = new GridBagConstraints();
		labelGbc_1.anchor = GridBagConstraints.EAST;
		labelGbc_1.insets = new Insets(5, 5, 5, 5);
		labelGbc_1.gridx = 0;
		labelGbc_1.gridy = 1;
		add(projectPathLabel, labelGbc_1);

		projectPathJTextField = new JTextField();
		GridBagConstraints componentGbc_1 = new GridBagConstraints();
		componentGbc_1.insets = new Insets(5, 0, 5, 5);
		componentGbc_1.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_1.gridx = 1;
		componentGbc_1.gridy = 1;
		add(projectPathJTextField, componentGbc_1);
		
		panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.gridx = 2;
		gbc_panel_2.gridy = 1;
		add(panel_2, gbc_panel_2);
		
		button = new JButton("");
		button.setIcon(new ImageIcon(GeneratorJPanel.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile(projectPathJTextField);
			}
		});
		button.setBackground(UIManager.getColor("Button.background"));
		panel_2.add(button);
		
		JLabel templatePathLabel = new JLabel("Template Path:");
		GridBagConstraints labelGbc_2 = new GridBagConstraints();
		labelGbc_2.anchor = GridBagConstraints.BASELINE_TRAILING;
		labelGbc_2.insets = new Insets(5, 5, 5, 5);
		labelGbc_2.gridx = 0;
		labelGbc_2.gridy = 2;
		add(templatePathLabel, labelGbc_2);
		
		templatePathJTextField = new JTextField();
		GridBagConstraints componentGbc_2 = new GridBagConstraints();
		componentGbc_2.anchor = GridBagConstraints.BASELINE;
		componentGbc_2.insets = new Insets(5, 0, 5, 5);
		componentGbc_2.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_2.gridx = 1;
		componentGbc_2.gridy = 2;
		add(templatePathJTextField, componentGbc_2);
		
		panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.anchor = GridBagConstraints.BASELINE;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_3.gridx = 2;
		gbc_panel_3.gridy = 2;
		add(panel_3, gbc_panel_3);
		
		templatePathChooserButton = new JButton("");
		templatePathChooserButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile(templatePathJTextField);
			}
		});
		templatePathChooserButton.setIcon(new ImageIcon(GeneratorJPanel.class.getResource("/com/sun/java/swing/plaf/windows/icons/Directory.gif")));
		templatePathChooserButton.setBackground(UIManager.getColor("Button.background"));
		panel_3.add(templatePathChooserButton);

		JLabel domainNameLabel = new JLabel("Domain Object Name:");
		GridBagConstraints labelGbc_3 = new GridBagConstraints();
		labelGbc_3.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
		labelGbc_3.insets = new Insets(5, 5, 5, 5);
		labelGbc_3.gridx = 0;
		labelGbc_3.gridy = 3;
		add(domainNameLabel, labelGbc_3);

		domainNameJTextField = new JTextField();
		GridBagConstraints componentGbc_3 = new GridBagConstraints();
		componentGbc_3.anchor = GridBagConstraints.ABOVE_BASELINE;
		componentGbc_3.insets = new Insets(5, 0, 5, 5);
		componentGbc_3.fill = GridBagConstraints.HORIZONTAL;
		componentGbc_3.gridx = 1;
		componentGbc_3.gridy = 3;
		add(domainNameJTextField, componentGbc_3);

		table = new MyTable();
		table.setRowSelectionAllowed(false);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		
		dataTypeCombobox = generateBox();
		
		JLabel lblEntityAttributes = new JLabel("Attributes:");
		lblEntityAttributes.setBackground(SystemColor.inactiveCaption);
		GridBagConstraints gbc_lblEntityAttributes = new GridBagConstraints();
		gbc_lblEntityAttributes.insets = new Insets(0, 0, 5, 5);
		gbc_lblEntityAttributes.anchor = GridBagConstraints.EAST;
		gbc_lblEntityAttributes.gridx = 0;
		gbc_lblEntityAttributes.gridy = 4;
		add(lblEntityAttributes, gbc_lblEntityAttributes);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.anchor = GridBagConstraints.EAST;
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.VERTICAL;
		gbc_panel_4.gridx = 1;
		gbc_panel_4.gridy = 4;
		add(panel_4, gbc_panel_4);
		
		JLabel lblDatatype = new JLabel("Data Type:");
		lblDatatype.setBackground(SystemColor.inactiveCaptionBorder);
		panel_4.add(lblDatatype);
		panel_4.add(dataTypeCombobox);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints scrollPaneC = new GridBagConstraints();
		scrollPaneC.gridheight = 2;
		scrollPaneC.insets = new Insets(0, 0, 5, 5);
		scrollPaneC.fill = GridBagConstraints.BOTH;
		scrollPaneC.gridx = 1;
		scrollPaneC.gridy = 5;
		add(scrollPane, scrollPaneC);

		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridheight = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 2;
		gbc_panel_1.gridy = 5;
		add(panel_1, gbc_panel_1);
		btnAddAttributeRow = new JButton("+");
		btnAddAttributeRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    SwingUtilities.invokeLater(new Runnable() {
			        public void run() {
						DomainAttributeBean bean = new DomainAttributeBean();
						bean.setDataType((DataType)dataTypeCombobox.getSelectedItem());
						generatorSetupBean.getDomainAttributes().add(bean);
						tableBinding.unbind();
						tableBinding.bind();
			        }
			    });
			        
			}
		});
		panel_1.setLayout(new MigLayout("", "[41px]", "[23px][23px]"));
		panel_1.add(btnAddAttributeRow, "cell 0 0,alignx left,aligny top");

		btnRemoveAttibuteRow = new JButton("-");
		btnRemoveAttibuteRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    SwingUtilities.invokeLater(new Runnable() {
			        public void run() {
						int index = table.getSelectedRow();
						if (index > -1) {
							generatorSetupBean.getDomainAttributes().remove(index);
							tableBinding.unbind();
							tableBinding.bind();
						}
			        }
			    });
			}
		});
		panel_1.add(btnRemoveAttibuteRow, "cell 0 1,alignx center,aligny top");
		
		JLabel lblGenerate = new JLabel("Generate:");
		GridBagConstraints gbc_lblGenerate = new GridBagConstraints();
		gbc_lblGenerate.anchor = GridBagConstraints.EAST;
		gbc_lblGenerate.insets = new Insets(0, 0, 5, 5);
		gbc_lblGenerate.gridx = 0;
		gbc_lblGenerate.gridy = 7;
		add(lblGenerate, gbc_lblGenerate);
		
		generatorOptionPanel = new JPanel();
		generatorOptionPanel.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_generatorOptionPanel = new GridBagConstraints();
		gbc_generatorOptionPanel.gridwidth = 2;
		gbc_generatorOptionPanel.insets = new Insets(0, 0, 5, 0);
		gbc_generatorOptionPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_generatorOptionPanel.gridx = 1;
		gbc_generatorOptionPanel.gridy = 7;
		add(generatorOptionPanel, gbc_generatorOptionPanel);
		
		chckbxFrontend = new JCheckBox("Frontend");
		chckbxFrontend.setBackground(SystemColor.inactiveCaptionBorder);
		
		chckbxRestfulWebservices = new JCheckBox("Restful Webservices");
		chckbxRestfulWebservices.setBackground(SystemColor.inactiveCaptionBorder);
		
		chckbxBackendservices = new JCheckBox("BackendServices");
		chckbxBackendservices.setBackground(SystemColor.inactiveCaptionBorder);
		
		chckbxTestdata = new JCheckBox("Testdata");
		chckbxTestdata.setBackground(SystemColor.inactiveCaptionBorder);
		
		chckbxEntity = new JCheckBox("Entity");
		chckbxEntity.setBackground(SystemColor.inactiveCaptionBorder);
		GroupLayout gl_generatorOptionPanel = new GroupLayout(generatorOptionPanel);
		gl_generatorOptionPanel.setHorizontalGroup(
			gl_generatorOptionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_generatorOptionPanel.createSequentialGroup()
					.addGroup(gl_generatorOptionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_generatorOptionPanel.createSequentialGroup()
							.addGroup(gl_generatorOptionPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxEntity)
								.addComponent(chckbxBackendservices))
							.addGap(36)
							.addGroup(gl_generatorOptionPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxTestdata)
								.addComponent(chckbxFrontend)))
						.addComponent(chckbxRestfulWebservices))
					.addGap(75))
		);
		gl_generatorOptionPanel.setVerticalGroup(
			gl_generatorOptionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_generatorOptionPanel.createSequentialGroup()
					.addGroup(gl_generatorOptionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxEntity)
						.addComponent(chckbxFrontend))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_generatorOptionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(chckbxBackendservices)
						.addComponent(chckbxTestdata))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxRestfulWebservices)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		generatorOptionPanel.setLayout(gl_generatorOptionPanel);

		panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.anchor = GridBagConstraints.SOUTHEAST;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 8;
		add(panel, gbc_panel);

		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generatorService.generate(generatorSetupBean);
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

		if (generatorSetupBean != null) {
			m_bindingGroup = initDataBindings();
		}
	}

	private JComboBox<DataType> generateBox() {
		JComboBox<DataType> bx = new JComboBox<DataType>();
		for (DataType dataType : DataType.values()) {
			bx.addItem(dataType);
		}
		return bx;
	}
	


	protected BindingGroup initDataBindings() {
		BeanProperty<frontend.beans.GeneratorSetupBean, java.lang.String> projectNameProperty = BeanProperty
				.create("projectName");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty = BeanProperty.create("text");
		newProjectNameBinding = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, generatorSetupBean, projectNameProperty,
						projectNameJTextField, textProperty);
		newProjectNameBinding.bind();

		BeanProperty<frontend.beans.GeneratorSetupBean, java.lang.String> projectPathProperty = BeanProperty
				.create("projectPath");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_1 = BeanProperty.create("text");
		projectHomeBinding = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, generatorSetupBean, projectPathProperty,
						projectPathJTextField, textProperty_1);
		projectHomeBinding.bind();
		
		
		BeanProperty<frontend.beans.GeneratorSetupBean, java.lang.String> templatePathProperty = BeanProperty
				.create("templatePath");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_templatePath = BeanProperty.create("text");
		AutoBinding<frontend.beans.GeneratorSetupBean, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding_templatePath = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, generatorSetupBean, templatePathProperty,
						templatePathJTextField, textProperty_templatePath);
		autoBinding_templatePath.bind();
		

		BeanProperty<frontend.beans.GeneratorSetupBean, java.lang.String> domainNameProperty = BeanProperty
				.create("domainName");
		BeanProperty<javax.swing.JTextField, java.lang.String> textProperty_2 = BeanProperty.create("text");
		AutoBinding<frontend.beans.GeneratorSetupBean, java.lang.String, javax.swing.JTextField, java.lang.String> autoBinding_domain = Bindings
				.createAutoBinding(AutoBinding.UpdateStrategy.READ_WRITE, generatorSetupBean, domainNameProperty,
						domainNameJTextField, textProperty_2);
		autoBinding_domain.bind();
	    
        BeanProperty<GeneratorSetupBean, Boolean> entityProperty = BeanProperty.create("generateEntity");
        BeanProperty<JCheckBox, Boolean> selectedProperty = BeanProperty.create("selected");
        AutoBinding<GeneratorSetupBean, Boolean, JCheckBox, Boolean> autoBinding_entity = Bindings.createAutoBinding(
        		AutoBinding.UpdateStrategy.READ_WRITE,
        		generatorSetupBean, entityProperty, chckbxEntity, selectedProperty);
        autoBinding_entity.bind();
        
        BeanProperty<GeneratorSetupBean, Boolean> serviceProperty = BeanProperty.create("generateService");
        BeanProperty<JCheckBox, Boolean> selectedServiceProperty = BeanProperty.create("selected");
        AutoBinding<GeneratorSetupBean, Boolean, JCheckBox, Boolean> autoBinding_service = Bindings.createAutoBinding(
        		AutoBinding.UpdateStrategy.READ_WRITE,
        		generatorSetupBean, serviceProperty, chckbxBackendservices, selectedServiceProperty);
        autoBinding_service.bind();
        
        BeanProperty<GeneratorSetupBean, Boolean> restProperty = BeanProperty.create("generateRest");
        BeanProperty<JCheckBox, Boolean> selectedRestProperty = BeanProperty.create("selected");
        AutoBinding<GeneratorSetupBean, Boolean, JCheckBox, Boolean> autoBinding_rest = Bindings.createAutoBinding(
        		AutoBinding.UpdateStrategy.READ_WRITE,
        		generatorSetupBean, restProperty, chckbxRestfulWebservices, selectedRestProperty);
        autoBinding_rest.bind();
        
        BeanProperty<GeneratorSetupBean, Boolean> frontendProperty = BeanProperty.create("generateFrontend");
        BeanProperty<JCheckBox, Boolean> selectedFrontendProperty = BeanProperty.create("selected");
        AutoBinding<GeneratorSetupBean, Boolean, JCheckBox, Boolean> autoBinding_frontend = Bindings.createAutoBinding(
        		AutoBinding.UpdateStrategy.READ_WRITE,
        		generatorSetupBean, frontendProperty, chckbxFrontend, selectedFrontendProperty);
        autoBinding_frontend.bind();

		BindingGroup bindingGroup = new BindingGroup();
		bindingGroup.addBinding(newProjectNameBinding);
		bindingGroup.addBinding(projectHomeBinding);
		bindingGroup.addBinding(autoBinding_templatePath);
		bindingGroup.addBinding(autoBinding_domain);
		bindingGroup.addBinding(autoBinding_entity);
		bindingGroup.addBinding(autoBinding_service);
		bindingGroup.addBinding(autoBinding_rest);
		bindingGroup.addBinding(autoBinding_frontend);

		generatorSetupBean.getDomainAttributes().add(new DomainAttributeBean());
		tableBinding = SwingBindings.createJTableBinding(AutoBinding.UpdateStrategy.READ_WRITE,
				generatorSetupBean.getDomainAttributes(), table);
		
		table.setTableBinding(tableBinding);

		// define the properties to be used for the columns
		BeanProperty<DomainAttributeBean, Integer> max = BeanProperty.create("max");
		BeanProperty<DomainAttributeBean, Integer> min = BeanProperty.create("min");
		BeanProperty<DomainAttributeBean, java.lang.String> pattern = BeanProperty.create("pattern");
		BeanProperty<DomainAttributeBean, java.lang.String> columnName = BeanProperty.create("columnName");
		BeanProperty<DomainAttributeBean, DataType> dataType = BeanProperty.create("dataType");
		BeanProperty<DomainAttributeBean, Boolean> nullable = BeanProperty.create("nullable");

		// configure how the properties map to columns
		tableBinding.addColumnBinding(columnName).setColumnName("Name").setEditable(true);
		tableBinding.addColumnBinding(dataType).setColumnName("Data Type").setColumnClass(DataType.class).setEditable(true);
		tableBinding.addColumnBinding(nullable).setColumnName("Nullable").setColumnClass(Boolean.class).setEditable(true);
		tableBinding.addColumnBinding(max).setColumnName("Max").setColumnClass(Integer.class).setEditable(true);
		tableBinding.addColumnBinding(min).setColumnName("Min").setColumnClass(Integer.class).setEditable(true);
		tableBinding.addColumnBinding(pattern).setColumnName("Pattern").setEditable(true);
		
		// realize the binding
		tableBinding.bind();
		
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.getColumnModel().getColumn(1).setCellEditor(createCellEditor(dataTypeCombobox));		

		
		bindingGroup.addBinding(tableBinding);
		return bindingGroup;
	}
	
	public TableCellEditor createCellEditor(JComboBox<DataType> combo){
		  DefaultCellEditor editor=new DefaultCellEditor(combo);
		  editor.setClickCountToStart(1);
		  return editor;
		}

	public frontend.beans.GeneratorSetupBean getGeneratorSetupBean() {
		return generatorSetupBean;
	}
	
	public BindingGroup getBindingGroup() {
		return m_bindingGroup;
	}

	public void setGeneratorSetupBean(frontend.beans.GeneratorSetupBean newGeneratorSetupBean) {
		setGeneratorSetupBean(newGeneratorSetupBean, true);
	}

	public void setGeneratorSetupBean(frontend.beans.GeneratorSetupBean newGeneratorSetupBean, boolean update) {
		generatorSetupBean = newGeneratorSetupBean;
		if (update) {
			if (m_bindingGroup != null) {
				m_bindingGroup.unbind();
				m_bindingGroup = null;
			}
			if (generatorSetupBean != null) {
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

	public JTableBinding<DomainAttributeBean, List<DomainAttributeBean>, JTable> getTableBinding() {
		return tableBinding;
	}

	public MyTable getTable() {
		return table;
	}   
}
