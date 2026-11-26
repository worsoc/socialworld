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
package org.socialworld.visualize;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.EnumMap;
import java.util.Map;

import org.socialworld.actions.handle.InventoryPlace;

/**
 * Where the slots of the inventory window lie (2026-11-24), as fractions of the panel (0..1), so that the picture
 * grows with the window. The figure looks at the viewer: the right hand of the human is on the left of the picture
 * (I2). Three columns: right side of the human, the body, left side. Each slot at the side has an anchor on the
 * figure, a thin line connects them.
 */
final class InventorySlotLayout {

	static final double COLUMN_RIGHT_SIDE = 0.02;	// the right side of the human, left in the picture
	static final double COLUMN_BODY = 0.36;
	static final double COLUMN_LEFT_SIDE = 0.70;
	static final double SLOT_WIDTH = 0.28;
	static final double SLOT_HEIGHT = 0.05;

	// the figure
	static final Point2D.Double HEAD = new Point2D.Double(0.5, 0.12);
	static final double HEAD_RADIUS = 0.04;
	static final Point2D.Double NECK = new Point2D.Double(0.5, 0.16);
	static final Point2D.Double SHOULDERS = new Point2D.Double(0.5, 0.20);
	static final Point2D.Double HIP = new Point2D.Double(0.5, 0.52);
	static final Point2D.Double RIGHT_HAND = new Point2D.Double(0.36, 0.42);
	static final Point2D.Double LEFT_HAND = new Point2D.Double(0.64, 0.42);
	static final Point2D.Double RIGHT_FOOT = new Point2D.Double(0.43, 0.88);
	static final Point2D.Double LEFT_FOOT = new Point2D.Double(0.57, 0.88);
	static final Point2D.Double MOUTH = new Point2D.Double(0.52, 0.13);
	static final Point2D.Double WAIST = new Point2D.Double(0.5, 0.48);
	static final Point2D.Double BACK = new Point2D.Double(0.47, 0.30);
	static final Point2D.Double SHOULDER_LEFT = new Point2D.Double(0.53, 0.21);
	static final Point2D.Double CHEST = new Point2D.Double(0.48, 0.25);
	static final Point2D.Double CLOAK = new Point2D.Double(0.52, 0.19);

	private static final Map<InventoryPlace, Rectangle2D.Double> SLOTS = new EnumMap<>(InventoryPlace.class);
	private static final Map<InventoryPlace, Point2D.Double> ANCHORS = new EnumMap<>(InventoryPlace.class);
	private static final Map<InventoryPlace, String> LABELS = new EnumMap<>(InventoryPlace.class);

	static {
		body(InventoryPlace.head, 0.02, "Kopf");
		body(InventoryPlace.upperBody, 0.21, "Oberk\u00F6rper");
		body(InventoryPlace.dress, 0.28, "Kleid");
		body(InventoryPlace.overgarmentArmour, 0.35, "R\u00FCstung");
		body(InventoryPlace.belt2, 0.46, "G\u00FCrtel 2");
		body(InventoryPlace.legs, 0.62, "Beine");

		side(InventoryPlace.neck, COLUMN_RIGHT_SIDE, 0.08, NECK, "Hals");
		side(InventoryPlace.overgarmentFront, COLUMN_RIGHT_SIDE, 0.14, CHEST, "\u00DCberwurf vorn");
		side(InventoryPlace.rightHand, COLUMN_RIGHT_SIDE, 0.26, RIGHT_HAND, "rechte Hand");
		side(InventoryPlace.rightHandGlove, COLUMN_RIGHT_SIDE, 0.33, RIGHT_HAND, "rechter Handschuh");
		side(InventoryPlace.belt1, COLUMN_RIGHT_SIDE, 0.46, WAIST, "G\u00FCrtel 1");
		side(InventoryPlace.back, COLUMN_RIGHT_SIDE, 0.58, BACK, "R\u00FCcken");
		side(InventoryPlace.rightFootSock, COLUMN_RIGHT_SIDE, 0.80, RIGHT_FOOT, "rechte Socke");
		side(InventoryPlace.rightFootShoe, COLUMN_RIGHT_SIDE, 0.87, RIGHT_FOOT, "rechter Schuh");

		side(InventoryPlace.mouth, COLUMN_LEFT_SIDE, 0.08, MOUTH, "Mund");
		side(InventoryPlace.overgarmentCloak, COLUMN_LEFT_SIDE, 0.14, CLOAK, "Umhang");
		side(InventoryPlace.leftHand, COLUMN_LEFT_SIDE, 0.26, LEFT_HAND, "linke Hand");
		side(InventoryPlace.leftHandGlove, COLUMN_LEFT_SIDE, 0.33, LEFT_HAND, "linker Handschuh");
		side(InventoryPlace.finger, COLUMN_LEFT_SIDE, 0.40, LEFT_HAND, "Finger");
		side(InventoryPlace.belt3, COLUMN_LEFT_SIDE, 0.46, WAIST, "G\u00FCrtel 3");
		side(InventoryPlace.bag, COLUMN_LEFT_SIDE, 0.58, SHOULDER_LEFT, "Tasche");
		side(InventoryPlace.leftFootSock, COLUMN_LEFT_SIDE, 0.80, LEFT_FOOT, "linke Socke");
		side(InventoryPlace.leftFootShoe, COLUMN_LEFT_SIDE, 0.87, LEFT_FOOT, "linker Schuh");
	}

	private InventorySlotLayout() {}

	private static void body(InventoryPlace place, double y, String label) {
		SLOTS.put(place, new Rectangle2D.Double(COLUMN_BODY, y, SLOT_WIDTH, SLOT_HEIGHT));
		LABELS.put(place, label);
	}

	private static void side(InventoryPlace place, double x, double y, Point2D.Double anchor, String label) {
		SLOTS.put(place, new Rectangle2D.Double(x, y, SLOT_WIDTH, SLOT_HEIGHT));
		ANCHORS.put(place, anchor);
		LABELS.put(place, label);
	}

	/** The slot of the place as fractions of the panel, null for noWhere. */
	static Rectangle2D.Double slot(InventoryPlace place) {
		return SLOTS.get(place);
	}

	/** The point on the figure a slot at the side is connected to, null for a slot on the body. */
	static Point2D.Double anchor(InventoryPlace place) {
		return ANCHORS.get(place);
	}

	static String label(InventoryPlace place) {
		String label = LABELS.get(place);
		return (label == null) ? place.name() : label;
	}
}
