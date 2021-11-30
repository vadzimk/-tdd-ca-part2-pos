package link.vadzimk.pos.test;

import org.junit.Before;
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
    private Display display;
    private Sale sale;

    @Before
    public void setUp() {
        display = new Display();
        sale = new Sale(display, new HashMap<>() {{
            put("12345", "8.0");
            put("23456", "12.50");
        }});
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

        public void setText(String text) {
            this.text = text;
        }
    }

    static class Sale {
        private final Display display;
        private final Map<String, String> priceByBarcode;

        public Sale(Display display, Map<String, String> priceByBarcode) {
            this.display = display;
            this.priceByBarcode = priceByBarcode;
        }

        public void onBarcode(String barcode) {
            // refused request, move this up the call stack?
            if ("".equals(barcode)) {  // guard clause
                displayEmptyBarcodeMsg();
                return;
            }


            String priceTxt = findPrice(barcode);
            if (priceTxt != null) {
                displayPrice(priceTxt);
            } else {
                displayProductNotFoundMsg(barcode);
            }

        }

        private boolean hasBarcode(String barcode) {
            return priceByBarcode.containsKey(barcode);
        }

        private String findPrice(String barcode) {
            return priceByBarcode.get(barcode);
        }

        private void displayPrice(String priceTxt) {
            display.setText(priceTxt);
        }

        private void displayProductNotFoundMsg(String barcode) {
            display.setText("product not found for " + barcode);
        }

        private void displayEmptyBarcodeMsg() {
            display.setText("Scanning error: empty barcode");
        }
    }
}
