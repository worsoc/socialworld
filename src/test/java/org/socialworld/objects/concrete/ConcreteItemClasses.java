/*
 * Social World
 * Copyright (C) 2026  Mathias Sikos
 * Written with the assistance of Claude (Anthropic), 2026
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

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.socialworld.objects.Item;

/**
 * For tests: all concrete (not abstract) subclasses of Item below the package objects.concrete (2026-11-24), found in
 * every class directory of the package (it is there in the test classes and in the main classes). A new item class
 * is found without being listed.
 */
public final class ConcreteItemClasses {

	public static final String PACKAGE = "org.socialworld.objects.concrete";

	private ConcreteItemClasses() {}

	public static List<Class<? extends Item>> find() throws URISyntaxException, ClassNotFoundException, IOException {
		List<Class<? extends Item>> found = new ArrayList<>();
		Enumeration<URL> urls = ConcreteItemClasses.class.getClassLoader().getResources(PACKAGE.replace('.', '/'));
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
}
