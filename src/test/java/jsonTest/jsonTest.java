package jsonTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jfr.Description;
import modal.Ware;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class jsonTest {
        ClassLoader cl = jsonTest.class.getClassLoader();

        ObjectMapper mapper = new ObjectMapper();

        @Description("Разбор json файла библиотекой Jackson")
        @Test
        void jsonTest() throws Exception {
                try (InputStream is = cl.getResourceAsStream("ware.json");
                     InputStreamReader isr = new InputStreamReader(is)) {
                        Ware ware = mapper.readValue(isr, Ware.class);
                        assertEquals("NewBalance", ware.getName());
                        assertEquals(5500, ware.getPrice());
                        assertEquals("Nike", ware.getBrand());
                        assertEquals(39, ware.getSize());
                        assertEquals(List.of("shoes", "leather", "leather"), ware.getCategory());

                }

        }
}
