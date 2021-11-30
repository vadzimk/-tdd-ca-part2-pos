package link.vadzimk.pos.test;

import org.junit.Before;
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
    private Display display;
    private Sale sale;

    @Before
    public void setUp() {
        display = new Display();
        sale = new Sale(display, new Catalog(new HashMap<>() {{
            put("12345", "8.0");
            put("23456", "12.50");
        }}));
    }

    @Test
    public void productFound() {
        sale.onBarcode("12345");
        assertEquals("8.0", display.getText());
    }

    @Test
    public void anotherProductFound() {
        sale.onBarcode("23456");
        assertEquals("12.50", display.getText());
    }

    @Test
    public void productNotFound() {
        sale.onBarcode("9999");
        assertEquals("product not found for 9999", display.getText());
    }

    @Test
    public void emptyBarcode() {
        sale.onBarcode("");
        assertEquals("Scanning error: empty barcode", display.getText());
    }

    static class Display {
        private String text;

        public String getText() {
            return text;
        }

        private void setText(String text) {
            this.text = text;
        }

        // should go to display
        public void displayPrice(String priceTxt) {
            setText(priceTxt);
        }

        // should go to display
        public void displayProductNotFoundMsg(String barcode) {
            setText("product not found for " + barcode);
        }

        // should go to display
        public void displayEmptyBarcodeMsg() {
            setText("Scanning error: empty barcode");
        }
    }

    static class Catalog {
        private final Map<String, String> priceByBarcode;

        public Catalog(Map<String, String> priceByBarcode){
            this.priceByBarcode = priceByBarcode;
        }
        private boolean hasBarcode(String barcode) {
            return priceByBarcode.containsKey(barcode);
        }

        private String findPrice(String barcode) {
            return priceByBarcode.get(barcode);
        }

    }

    static class Sale {
        private final Display display;
        private final Catalog catalog;

        public Sale(Display display, Catalog catalog) {
            this.display = display;
            this.catalog = catalog;
        }

        public void onBarcode(String barcode) {
            // refused request, move this up the call stack?
            if ("".equals(barcode)) {  // guard clause
                display.displayEmptyBarcodeMsg();
                return;
            }

            String priceTxt = catalog.findPrice(barcode);
            if (priceTxt != null) {
                display.displayPrice(priceTxt);
            } else {
                display.displayProductNotFoundMsg(barcode);
            }
        }
    }
}
