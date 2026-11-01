package org.socialworld.tools.mct;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Alle gültigen Raster eines Ordners. Ob eine Datei dazugehört, entscheidet ihr Inhalt (siehe LTileRaster).
 * Nur im MapVisualizer verworfene Dateien (tileterm_deletable_) werden bewusst nicht aufgenommen.
 */
public class LTileRasterLibrary {

	private static final String REJECTED_PREFIX = "tileterm_deletable_";

	public static final class Entry {
		private final String fileName;
		private final LTileRaster raster;

		private Entry(String fileName, LTileRaster raster) {
			this.fileName = fileName;
			this.raster = raster;
		}

		public String getFileName() { return fileName; }
		public LTileRaster getRaster() { return raster; }
	}

	private File directory;
	private List<Entry> entries = Collections.emptyList();
	private int skippedFileCount;

	public void load(File directory) {
		List<Entry> loaded = new ArrayList<>();
		int skipped = 0;

		File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt") && !name.startsWith(REJECTED_PREFIX));
		if (files != null) {
			Arrays.sort(files);
			for (File file : files) {
				try {
					loaded.add(new Entry(file.getName(), LTileRaster.fromFile(file)));
				} catch (IOException | IllegalArgumentException e) {
					skipped++;
				}
			}
		}

		this.directory = directory;
		this.entries = Collections.unmodifiableList(loaded);
		this.skippedFileCount = skipped;
	}

	public boolean isLoaded() { return directory != null; }
	public File getDirectory() { return directory; }
	public List<Entry> getEntries() { return entries; }

	/** Anzahl der .txt-Dateien, die keine gültigen Raster sind, zum Beispiel analyzer_results.txt. */
	public int getSkippedFileCount() { return skippedFileCount; }
}
