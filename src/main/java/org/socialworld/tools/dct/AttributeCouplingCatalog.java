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
package org.socialworld.tools.dct;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.socialworld.attributes.Attribute;
import org.socialworld.calculation.functions.FunctionByTable_Type;
import org.socialworld.tools.dct.AttributeCoupling.Moderation;

/**
 * The couplings between attributes and the signatures of distinctive characters.
 * See attribute_matrix_characters.md, sections 5 and 10.
 */
public final class AttributeCouplingCatalog {

	public static final String SIGNATURES_RESOURCE = "coupling_signatures.csv";

	private static final FunctionByTable_Type HIGH = FunctionByTable_Type.hinge_high;
	private static final FunctionByTable_Type LOW = FunctionByTable_Type.hinge_low;

	private final Map<Integer, List<String[]>> signatures;

	private AttributeCouplingCatalog(Map<Integer, List<String[]>> signatures) {
		this.signatures = signatures;
	}

	public static AttributeCouplingCatalog load() {
		try (InputStream in = AttributeCouplingCatalog.class.getResourceAsStream(SIGNATURES_RESOURCE)) {
			if (in == null) throw new IllegalStateException(SIGNATURES_RESOURCE + " not found");
			return parse(new String(in.readAllBytes(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	/**
	 * Lines: no;coupling;set;value  or  no;coupling;times;factor  or  no;coupling;new;source;target;function;strength
	 */
	static AttributeCouplingCatalog parse(String content) {
		Map<Integer, List<String[]>> signatures = new HashMap<>();
		for (String rawLine : content.split("\\R")) {
			String line = rawLine.trim();
			if (line.isEmpty() || line.startsWith("#")) continue;
			String[] fields = line.split(";", -1);
			boolean valid = fields.length >= 4
					&& ((fields[2].equals("new") && fields.length == 7) || (!fields[2].equals("new") && fields.length == 4));
			if (!valid || !(fields[2].equals("set") || fields[2].equals("times") || fields[2].equals("new"))) {
				throw new IllegalArgumentException("wrong signature line: " + line);
			}
			signatures.computeIfAbsent(Integer.parseInt(fields[0].trim()), key -> new ArrayList<>()).add(fields);
		}
		return new AttributeCouplingCatalog(signatures);
	}

	private static AttributeCoupling coupling(String id, Attribute source, Attribute target, FunctionByTable_Type function,
			double strength, Attribute moderator, Moderation moderation) {
		return new AttributeCoupling(id, source, target, function, (float) strength, moderator, moderation);
	}

	/** The couplings every character has, before its signature. */
	static List<AttributeCoupling> standardCouplings() {
		List<AttributeCoupling> list = new ArrayList<>();
		Attribute mo = Attribute.mood, co = Attribute.courage, mr = Attribute.morals, ma = Attribute.materialism;
		Attribute ti = Attribute.tiredness, cu = Attribute.curiosity, sp = Attribute.spirituality, hu = Attribute.hunger, pw = Attribute.power;

		list.add(coupling("K1", hu, pw, HIGH, -0.40, pw, Moderation.proportionalPower));
		list.add(coupling("K2", ti, pw, HIGH, -0.50, pw, Moderation.proportionalPower));
		list.add(coupling("K3", hu, mo, HIGH, -0.30, sp, Moderation.dampens));
		list.add(coupling("K4", ti, mo, HIGH, -0.40, mo, Moderation.dampens));
		list.add(coupling("K5", ti, cu, HIGH, -0.10, cu, Moderation.dampens));
		list.add(coupling("K6", ti, co, HIGH, -0.08, co, Moderation.dampens));
		list.add(coupling("K7", hu, mr, HIGH, -0.026, mr, Moderation.dampens));
		list.add(coupling("K8", hu, ma, HIGH, 0.026, ma, Moderation.amplifies));
		list.add(coupling("K9", mo, sp, LOW, 0.02, sp, Moderation.amplifies));
		list.add(coupling("K10", mo, cu, HIGH, 0.06, cu, Moderation.amplifies));
		list.add(coupling("K11", mo, co, LOW, -0.06, co, Moderation.dampens));
		list.add(coupling("K12", pw, co, HIGH, 0.04, co, Moderation.amplifies));
		list.add(coupling("K13", pw, mo, LOW, -0.10, mo, Moderation.dampens));
		list.add(coupling("K14", mo, ti, FunctionByTable_Type.v, 0.03, null, Moderation.none));
		return list;
	}

	/**
	 * @return id to strength in displayed points per step, with level factors and signature,
	 *         without the individual value
	 */
	Map<AttributeCoupling, Float> strengthsFor(AttributeCharacter character, int targetPower) {
		Map<String, AttributeCoupling> couplings = new LinkedHashMap<>();
		Map<String, Float> strengths = new LinkedHashMap<>();
		for (AttributeCoupling coupling : standardCouplings()) {
			AttributeLevel level = coupling.moderator == null ? AttributeLevel.average
					: character.getLevel(coupling.moderator.getIndex());
			couplings.put(coupling.id, coupling);
			strengths.put(coupling.id, coupling.strength * coupling.factorFor(level, targetPower));
		}

		for (String[] change : signatures.getOrDefault(character.getNumber(), Collections.emptyList())) {
			String id = change[1].trim();
			switch (change[2]) {
			case "set":
				requireKnown(couplings, id);
				strengths.put(id, Float.parseFloat(change[3].trim()));
				break;
			case "times":
				requireKnown(couplings, id);
				strengths.put(id, strengths.get(id) * Float.parseFloat(change[3].trim()));
				break;
			default:
				float strength = Float.parseFloat(change[6].trim());
				couplings.put(id, new AttributeCoupling(id, Attribute.fromName(change[3].trim()), Attribute.fromName(change[4].trim()),
						FunctionByTable_Type.valueOf(change[5].trim()), strength, null, Moderation.none));
				strengths.put(id, strength);
			}
		}

		Map<AttributeCoupling, Float> result = new LinkedHashMap<>();
		for (Map.Entry<String, AttributeCoupling> entry : couplings.entrySet()) {
			result.put(entry.getValue(), strengths.get(entry.getKey()));
		}
		return result;
	}

	private static void requireKnown(Map<String, AttributeCoupling> couplings, String id) {
		if (!couplings.containsKey(id)) throw new IllegalArgumentException("unknown coupling " + id);
	}
}
