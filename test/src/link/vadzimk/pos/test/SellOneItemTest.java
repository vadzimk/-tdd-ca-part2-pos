package link.vadzimk.pos.test;

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
        final Sale sale = new Sale();

        sale.onBarcode("12345");
        assertEquals("8.0", display.getText());
    }

    static class Display{
        public String getText(){
            return "8.0";
        }
    }
    static class Sale{
        public void onBarcode(String barcode){

        }
    }
}
