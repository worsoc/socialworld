package org.socialworld.tools.mct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Supplier;

/**
 * Erzeugt ein Raster über MapSkeletonGenerator und den Solver des MapCreationTools.
 * Der Solver arbeitet mit statischem Zustand, es darf also immer nur ein Aufruf gleichzeitig laufen.
 */
public final class LTileRasterGenerator {

	private static final int MAX_EMPTY_SKELETONS = 100;

	private static volatile boolean cancelled;

	private LTileRasterGenerator() {
	}

	/**
	 * @param edgeSupplier liefert für jeden Versuch die vier Ränder {Nord, Ost, Süd, West}, oder null,
	 *                     wenn es keine passenden Ränder gibt
	 * @return das Raster oder null, wenn es innerhalb von timeoutMillis keines gab, die Ränder
	 *         unlösbar sind oder abgebrochen wurde
	 */
	public static LTileRaster generate(Supplier<int[][]> edgeSupplier, long timeoutMillis) throws IOException {
		cancelled = false;
		long deadline = System.currentTimeMillis() + timeoutMillis;
		int emptySkeletons = 0;

		File target = File.createTempFile("gte_raster_", ".txt");
		try {
			while (!cancelled && System.currentTimeMillis() < deadline && emptySkeletons < MAX_EMPTY_SKELETONS) {
				int[][] edges = edgeSupplier.get();
				if (edges == null) {
					return null;
				}

				String skeleton = MapSkeletonGenerator.generateSkeleton(edges[0], edges[1], edges[2], edges[3]);
				if (skeleton.isEmpty()) {
					emptySkeletons++;
					continue;
				}

				long remaining = deadline - System.currentTimeMillis();
				if (MapCreationTool.fillSkeleton(skeleton, target.getAbsolutePath(), remaining)) {
					try {
						return LTileRaster.fromFile(target);
					} catch (IllegalArgumentException invalidResult) {
						// Nächster Versuch: Der Solver hat kein gültiges Raster geliefert.
					}
				}
			}
			return null;
		} finally {
			Files.deleteIfExists(target.toPath());
		}
	}

	/** Bricht einen laufenden Aufruf von generate ab. */
	public static void cancel() {
		cancelled = true;
		MapCreationTool.cancelFillSkeleton();
	}
}
