package org.socialworld.tools.pct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.socialworld.calculation.descriptions.EventPerceptionDescription;
import org.socialworld.core.EventType;

import com.google.gson.Gson;

public class PerceptionCreationToolTest {

  private static final Path EXISTING_DATA = Paths.get("src/test/swpool_epd.sql");
  private static final int COUNT_ROWS = 40;

  @TempDir
  Path tempDir;

  // ---------------------------------------------------------------------------
  // acceptance: the tool produces the existing data
  // ---------------------------------------------------------------------------

  @Test
  void writtenFile_isByteForByteTheExistingData() throws IOException {
    // The SQL file is not part of the repository: without it the test is skipped.
    assumeTrue(Files.exists(EXISTING_DATA), EXISTING_DATA + " is not available");

    Path file = tempDir.resolve("new.sql");

    PerceptionCreationTool.main(new String[] {file.toString()});

    assertThat(Files.readAllBytes(file)).isEqualTo(Files.readAllBytes(EXISTING_DATA));
  }

  @Test
  void everyRow_isLoadedLikeThePoolDoesIt() {
    Matcher row = Pattern.compile("\\(508,(\\d+),'(.*?)'\\)\\s*[,;]").matcher(PerceptionSqlFile.createContent());
    int countRows = 0;

    while (row.find()) {
      EventPerceptionDescription description = new EventPerceptionDescription(new Gson(), row.group(2));
      description.setFunctions();

      assertThat(description.getEventType()).isEqualTo(EventType.percipientExistsDistance5000);
      assertThat(description.getPerceptionType()).isEqualTo(Integer.parseInt(row.group(1)));
      assertThat(description.countFunctions()).as("row " + row.group(1)).isEqualTo(1);
      countRows++;
    }

    assertThat(countRows).isEqualTo(COUNT_ROWS);
  }

  // ---------------------------------------------------------------------------
  // the tool does not destroy anything
  // ---------------------------------------------------------------------------

  @Test
  void existingFile_staysAsItIs() throws IOException {
    Path file = tempDir.resolve("existing.sql");
    byte[] content = "must not change".getBytes(StandardCharsets.UTF_8);
    Files.write(file, content);

    PerceptionCreationTool.main(new String[] {file.toString()});

    assertThat(Files.readAllBytes(file)).isEqualTo(content);
  }

  @Test
  void withoutArgument_nothingIsWritten() throws IOException {
    PerceptionCreationTool.main(new String[] {});

    try (java.util.stream.Stream<Path> files = Files.list(tempDir)) {
      assertThat(files).isEmpty();
    }
  }

  // ---------------------------------------------------------------------------
  // what the tool reports
  // ---------------------------------------------------------------------------

  /**
   * Material of "all animals and items" is not offered by the meta information, because animals
   * only result in colour. mainColour_2 is a name of its own for an offered path.
   */
  @Test
  void templates_reportThePathsOutsideOfTheMetaInformation() {
    List<PerceptionPath> outside = PerceptionTemplates.findPathsOutsideOfMetaInformation();

    assertThat(outside).extracting(PerceptionPath::getValueName).containsExactlyInAnyOrder(
        "inventory_shirt.SOGN_184549375.mainMaterial",
        "inventory_cap.SOGN_184549375.mainMaterial",
        "inventory_cap.SOGN_184549375.mainColour_2");
  }
}
