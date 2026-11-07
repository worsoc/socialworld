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

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class TextFileTest {

  private static final String TEXT = "Es war einmal ein König, der hatte drei Söhne.";

  @TempDir
  Path tempDir;

  @Test
  void readsUtf8() throws IOException {
    Path file = tempDir.resolve("utf8.txt");
    Files.write(file, TEXT.getBytes(StandardCharsets.UTF_8));

    assertThat(TextFile.read(file)).isEqualTo(TEXT);
  }

  @Test
  void fallsBackToIso88591() throws IOException {
    Path file = tempDir.resolve("latin1.txt");
    Files.write(file, TEXT.getBytes(StandardCharsets.ISO_8859_1));

    assertThat(TextFile.read(file)).isEqualTo(TEXT);
  }
}
