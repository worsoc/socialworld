package org.socialworld.datasource.tablesSimulation.propertySets;

import org.socialworld.datasource.mariaDB.Table;

public abstract class TableSet extends Table {


	public abstract void insert(int set_id, int lfd_nr, int element,  int share);

}
