package fileTests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.pdftest.PDF.containsExactText;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Check and read all files from ZipFile")
public class ZipFileTest {

    public ClassLoader cl = ZipFileTest.class.getClassLoader();

    @DisplayName("Check and read pdf file from ZipFile")
    @Test
    void readPDFTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("DownloadsTest.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                final String name = entry.getName();
                if (name.contains(".pdf")) {
                    PDF pdf = new PDF(zis);
                    assertThat(pdf, containsExactText("Тестовый PDF-документ"));
                }
            }
        }
    }


    @DisplayName("Check and read xls file from ZipFile")
    @Test
    void readXLSXTest() throws Exception {
        ZipFile zipFile = new ZipFile(new File("src/test/resources/DownloadsTest.zip"));
        try (InputStream is = cl.getResourceAsStream("DownloadsTest.zip")) {
            assert is != null;
            try (ZipInputStream zipInputStream = new ZipInputStream(is)) {
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".xlsx"))
                        try (InputStream inputStreamXlsx = zipFile.getInputStream((ZipArchiveEntry) entry)) {
                            XLS xls = new XLS(inputStreamXlsx);
                            assertEquals("test1", xls.excel.getSheet("test1").getRow(1).getCell(1).toString());

                        }
                }
            }
        }
    }

    @DisplayName("Check and read csv file from ZipFile")
    @Test
    void readCSVTest() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("DownloadsTest.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                final String name = entry.getName();
                if (name.contains(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    assertEquals(3, content.size());

                    final String[] firstRow = content.get(0);
                    final String[] secondRow = content.get(1);
                    final String[] thirdRow = content.get(2);

                    Assertions.assertArrayEquals(new String[]{"Letters", "numbers"}, firstRow);
                    Assertions.assertArrayEquals(new String[]{"A", "1"}, secondRow);
                    Assertions.assertArrayEquals(new String[]{"B", "2"}, thirdRow);
                }
            }
        }
    }
}
