package link.vadzimk.pos.test;

import org.junit.Ignore;
import org.junit.Test;

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
//    @Ignore("refactoring")
    public void anotherProductFound() {
        final Display display = new Display();
        final Sale sale = new Sale(display);

        sale.onBarcode("23456");
        assertEquals("12.50", display.getText());
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

        public Sale(Display display) {
            this.display = display;
        }

        public void onBarcode(String barcode) {
            if ("12345".equals(barcode)) {
                display.setText("8.0");
            } else if("23456".equals(barcode)){
                display.setText("12.50");
            }
        }
    }
}
