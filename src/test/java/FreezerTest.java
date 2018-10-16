import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class FreezerTest {
    Freezer fr = new Freezer();
    @Test
    public void save_sendFile() throws IOException {
        byte[] arr = Files.readAllBytes(Paths.get("./pom.xml"));
        sendFile sf = new sendFile("pom.xml", arr);
        assertEquals(sf.name(), "pom.xml");
        sendFile restored = fr.restore_sendFile(fr.save_sendFile(sf));
        assertEquals(restored.name(), "pom.xml");
        assertEquals(restored.body().length, 6132);

    }
}