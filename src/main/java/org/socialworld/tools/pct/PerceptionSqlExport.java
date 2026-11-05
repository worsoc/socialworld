package org.socialworld.tools.pct;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writes the descriptions of all perceptions into a new SQL file.
 * The program writes nothing into the database: check the file and import it yourself.
 */
public final class PerceptionSqlExport {

	private PerceptionSqlExport() {
	}

	public static void main(String args[]) throws IOException {

		if (args.length != 1) {
			System.out.println("Usage: PerceptionSqlExport <new sql file>");
			return;
		}

		Path file = Paths.get(args[0]);
		if (Files.exists(file)) {
			System.out.println("The file already exists and stays as it is: " + file);
			return;
		}

		Files.write(file, PerceptionSqlFile.createContent().getBytes(StandardCharsets.UTF_8));
		System.out.println("Written: " + file);

		for (PerceptionPath path : PerceptionTemplates.findPathsOutsideOfMetaInformation()) {
			System.out.println("Not in the meta information: " + path);
		}
	}
}
