/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://gnu.org>.
 */
package org.socialworld.objects.concrete;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.socialworld.datasource.mariaDB.DatabaseAssumptions;
import org.socialworld.objects.Item;
import org.socialworld.objects.State;
import org.socialworld.objects.StateItem;
import org.socialworld.objects.WriteAccessToItem;

/**
 * Every concrete item gets its initial states (2026-11-24): most classes do not override getInitState, they take
 * it from their group (HumanCrafted, Food, Liquid, Solid, PlantPart, AnimalPart). SimulationObject.createAddOnStates
 * casts what getInitState gives, so a group that gave NoState would throw a ClassCastException when the item is
 * created (as Liquid did before 2026-11-23). The classes are found in the package objects.concrete, so a new class
 * is checked without being listed here.
 */
class AllItemsInitStateTest {

	private static final String PACKAGE = "org.socialworld.objects.concrete";

	/** All concrete (not abstract) subclasses of Item below the package. */
	static List<Class<? extends Item>> concreteItems() throws URISyntaxException, ClassNotFoundException, IOException {
		// the package is there twice, in the test classes and in the main classes: look in every directory of it
		List<Class<? extends Item>> found = new ArrayList<>();
		Enumeration<URL> urls = AllItemsInitStateTest.class.getClassLoader().getResources(PACKAGE.replace('.', '/'));
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			if ("file".equals(url.getProtocol())) collect(new File(url.toURI()), PACKAGE, found);
		}
		return found;
	}

	@SuppressWarnings("unchecked")
	private static void collect(File directory, String packageName, List<Class<? extends Item>> found) throws ClassNotFoundException {
		File[] files = directory.listFiles();
		if (files == null) return;
		for (File file : files) {
			if (file.isDirectory()) {
				collect(file, packageName + "." + file.getName(), found);
				continue;
			}
			String name = file.getName();
			if (!name.endsWith(".class") || name.contains("$")) continue;
			Class<?> type = Class.forName(packageName + "." + name.substring(0, name.length() - ".class".length()));
			if (Item.class.isAssignableFrom(type) && !Modifier.isAbstract(type.getModifiers()) && !type.isInterface()) {
				found.add((Class<? extends Item>) type);
			}
		}
	}

	private static State initState(Item item, Class<? extends State> stateClass) throws Exception {
		Method method = null;
		for (Class<?> type = item.getClass(); method == null && type != null; type = type.getSuperclass()) {
			try {
				method = type.getDeclaredMethod("getInitState", String.class);
			} catch (NoSuchMethodException e) {
				// look in the super class
			}
		}
		assertThat(method).as(item.getClass().getSimpleName() + ": getInitState").isNotNull();
		method.setAccessible(true);
		return (State) method.invoke(item, stateClass.getName());
	}

	@Test
	void allConcreteItems_areFound_atLeastThe104OfTheHierarchy() throws Exception {
		List<Class<? extends Item>> items = concreteItems();
		long withoutTestClasses = items.stream().filter(type -> !type.getSimpleName().startsWith("Test")).count();
		assertThat(withoutTestClasses).isGreaterThanOrEqualTo(104);
	}

	@Test
	void everyConcreteItem_givesTheThreeStates_andFoodTheEatableToo() throws Exception {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		List<String> wrong = new ArrayList<>();
		for (Class<? extends Item> type : concreteItems()) {
			Item item = type.getDeclaredConstructor().newInstance();
			String name = type.getSimpleName();
			if (!(initState(item, StatePerceptible.class) instanceof StatePerceptible)) wrong.add(name + ": StatePerceptible");
			if (!(initState(item, StateAppearance.class) instanceof StateAppearance)) wrong.add(name + ": StateAppearance");
			if (!(initState(item, StateComposition.class) instanceof StateComposition)) wrong.add(name + ": StateComposition");
			if (item instanceof Food && !(initState(item, StateEatable.class) instanceof StateEatable)) wrong.add(name + ": StateEatable");
		}
		assertThat(wrong).isEmpty();
	}

	@Test
	void everyConcreteItem_canBeCreatedWithItsState() throws Exception {
		DatabaseAssumptions.assumeDatabaseIsAvailable();	// a state reads its CacheTableState... from the database
		List<String> failed = new ArrayList<>();
		for (Class<? extends Item> type : concreteItems()) {
			Item item = type.getDeclaredConstructor().newInstance();
			try {
				// as CreateItem does it: the write access gives the item its state and the add-on states
				new WriteAccessToItem(item, new StateItem());
			} catch (RuntimeException e) {
				failed.add(type.getSimpleName() + ": " + e);
			}
		}
		assertThat(failed).isEmpty();
	}
}
