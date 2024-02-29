package ncc.up.pt.updateBackend.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class DatabaseDumpController {

    @GetMapping("/dump-database")
    public String dumpDatabase() {
        try {
            // Get the current working directory (project root)
            String workingDir = System.getProperty("user.dir");

            // Create the dump directory inside the working directory
            String dumpDirName = "database_dumps";
            String dumpDirPath = workingDir + File.separator + dumpDirName;

            // Create a directory with a timestamp
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String dirPath = dumpDirPath + File.separator + "dump_" + timestamp;
            File directory = new File(dirPath);
            if (!directory.exists()) {
                boolean isCreated = directory.mkdirs();
                if (!isCreated) {
                    return "Failed to create directory for database dump.";
                }
            }

            // Command to dump the database - Replace credentials and database details
            String command = "mysqldump -u root -pmarco update2024 > " + dirPath + File.separator + "dump.sql";

            Runtime.getRuntime().exec(command);
            return "Database dump initiated at " + dirPath;
        } catch (IOException e) {
            e.printStackTrace();
            return "Error during database dump";
        }
    }
}
