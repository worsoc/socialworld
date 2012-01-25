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
import org.socialworld.SocialWorld;
import org.socialworld.core.Simulation;
import org.socialworld.objects.Human;

public class View extends ViewPart {
	public static final String ID = "org.socialworld.simpleclient.view";

	private static final Logger logger = Logger.getLogger(View.class);

	private TableViewer viewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {

		private final PropertyChangeListener listener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {
				View.this.viewer.getControl().getDisplay().asyncExec(
						new Runnable() {

							public void run() {
								View.this.viewer.refresh();
							}

						});
			}
		};

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse
		 * .jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		public void inputChanged(final Viewer viewer, final Object oldInput,
				final Object newInput) {

			if (newInput instanceof IModel) {
				final IModel model = (IModel) newInput;
				model.addPropertyChangeListener(
						ListModel.KEY_LIST_CHANGE_PROPERTY, this.listener);
			}

			if (oldInput instanceof IModel) {
				final IModel model = (IModel) oldInput;
				model.removePropertyChangeListener(
						ListModel.KEY_LIST_CHANGE_PROPERTY, this.listener);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(
		 * java.lang.Object)
		 */
		@SuppressWarnings("unchecked")
		public Object[] getElements(final Object inputElement) {
			if (inputElement instanceof List) {
				final List humanList = (List<Human>) inputElement;
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

		public String getColumnText(final Object obj, final int index) {
			if (obj instanceof Human) {
				final Human human = (Human) obj;
				switch (index) {
				case 0:
					return human.toString();
				case 1:
					return "Position";
				default:
					return "IRGENDWAS";
				}

			}
			return "---";
		}

		public Image getColumnImage(final Object obj, final int index) {
			return null;
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(final Composite parent) {
		this.viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL
				| SWT.V_SCROLL);
		final Table table = this.viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		final TableColumn columnName = new TableColumn(table, SWT.BORDER);
		columnName.setText("Name");
		columnName.setWidth(100);
		final TableColumn columnPosition = new TableColumn(table, SWT.BORDER);
		columnPosition.setText("Position");
		columnPosition.setWidth(100);
		final TableColumn columnIrgendwas = new TableColumn(table, SWT.BORDER);
		columnIrgendwas.setText("Irgendwas");
		columnIrgendwas.setWidth(100);

		logger.debug("get simulation object");

		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setLabelProvider(new ViewLabelProvider());

		Simulation simulation = SocialWorld.getCurrent().getSimulation();
		// only for test visualize we use an public access to simulation's
		// object master
		final List<Human> humans = simulation.getObjectMaster().getHumans();

		logger.debug("Set input to viewer: " + humans);
		this.viewer.setInput(humans);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		this.viewer.getControl().setFocus();
	}
}