package ncc.up.pt.updateBackend.Controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

@RestController
public class DatabaseDownloadController {

    @GetMapping("/download-latest-dump")
    public ResponseEntity<Resource> downloadLatestDump() {
        try {
            String workingDir = System.getProperty("user.dir");
            String dumpDirPath = workingDir + File.separator + "database_dumps";

            // Find the latest dump file
            try (Stream<Path> paths = Files.walk(Paths.get(dumpDirPath))) {
                Path latestDump = paths
                        .filter(Files::isRegularFile)
                        .max(Comparator.comparingLong(p -> p.toFile().lastModified()))
                        .orElse(null);

                if (latestDump == null) {
                    return ResponseEntity.notFound().build();
                }

                // Set the content type and attachment header.
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=\"" + latestDump.getFileName().toString() + "\"")
                        .body(new FileSystemResource(latestDump.toFile()));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
