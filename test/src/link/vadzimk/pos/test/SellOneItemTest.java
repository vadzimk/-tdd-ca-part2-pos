package link.vadzimk.pos.test;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/*
 * The output of the system is the text of the price
 * The input is an event of scanning a barcode for the listener onBarcode(code: String): void
 * Test plan:
 * product found
 * product not found
 * empty barcode
 * null barcode
 * */

public class SellOneItemTest {
    @Test
    public void productFound() {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("12345");
        assertEquals("8.0", display.getText());
    }

    @Test
    public void anotherProductFound() {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("23456");
        assertEquals("12.50", display.getText());
    }

    @Test
    public void productNotFound() {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("9999");
        assertEquals("product not found for 9999", display.getText());
    }

    @Test
    public void emptyBarcode() {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("");
        assertEquals("Scanning error: empty barcode", display.getText());
    }

    static class Display {
        private String text;

        public String getText() {

            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    static class Sale {
        private final Display display;
        private final Map<String, String> priceByBarcode;

        public Sale(Display display) {
            this.display = display;
            this.priceByBarcode = new HashMap<>() {{
                put("12345", "8.0");
                put("23456", "12.50");
            }};
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.setText("Scanning error: empty barcode");
            } else {


                if (priceByBarcode.containsKey(barcode)) {
                    display.setText(priceByBarcode.get(barcode));
                } else {
                    display.setText("product not found for " + barcode);
                }
            }
        }
    }
}
