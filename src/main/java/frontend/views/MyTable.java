package frontend.views;

import java.util.List;

import javax.swing.JTable;

import org.jdesktop.swingbinding.JTableBinding;

import frontend.beans.DomainAttributeBean;

public class MyTable extends JTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JTableBinding<DomainAttributeBean, List<DomainAttributeBean>, JTable> tableBinding;

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		super.setValueAt(aValue, row, column);
		tableBinding.unbind();
		tableBinding.bind();
		revalidate();

	}

	public JTableBinding<DomainAttributeBean, List<DomainAttributeBean>, JTable> getTableBinding() {
		return tableBinding;
	}

	public void setTableBinding(JTableBinding<DomainAttributeBean, List<DomainAttributeBean>, JTable> tableBinding) {
		this.tableBinding = tableBinding;
	}
	

}
