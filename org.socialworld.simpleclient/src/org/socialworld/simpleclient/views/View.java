package org.socialworld.simpleclient.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;
import org.socialworld.IModel;
import org.socialworld.ListModel;
import org.socialworld.core.ObjectManager;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;

public class View extends ViewPart {
	public static final String ID = "org.socialworld.simpleclient.view";

	private static final Logger logger = Logger.getLogger(View.class);

	private TableViewer viewer;

	protected Simulation simulation;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {

		private PropertyChangeListener listener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				viewer.getControl().getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						viewer.refresh();
					}

				});
			}
		};

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer,
		 *      java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(final Viewer viewer, Object oldInput,
				Object newInput) {

			if (newInput instanceof IModel) {
				IModel model = (IModel) newInput;
				model.addPropertyChangeListener(
						ListModel.KEY_LIST_CHANGE_PROPERTY, listener);
			}

			if (oldInput instanceof IModel) {
				IModel model = (IModel) oldInput;
				model.removePropertyChangeListener(
						ListModel.KEY_LIST_CHANGE_PROPERTY, listener);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
		 */
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof List) {
				List humanList = (List) inputElement;
				return humanList.toArray();
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
		 */
		public void dispose() {
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider {

		public String getColumnText(Object obj, int index) {
			if (obj instanceof Human) {
				Human human = (Human) obj;
				return human.toString();
			}
			return "---";
		}

		public Image getColumnImage(Object obj, int index) {
			return null;
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		Table table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		TableColumn column = new TableColumn(table, SWT.BORDER);
		column.setText("Name");
		column.setWidth(100);
		
		logger.debug("get simulation object");
		simulation = ObjectManager.getObjectManager().getSimulation();

		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());

		// List<Human> humans = new ListModel<Human>();
		// humans.add(new Human());
		// humans.add(new Human());
		// humans.add(new Human());

		List<Human> humans = simulation.getHumans();

		logger.debug("Set input to viewer: " + humans);
		viewer.setInput(humans);

		humans.add(new Human());
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}