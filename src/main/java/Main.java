
import example.Address;
import example.Customer;
import example.Name;
import example.Order;
import org.jibx.runtime.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: magzhan.karasayev
 * Date: 08.01.14
 * Time: 16:22
 */
public class Main {
    public static void main(String[] args) throws Exception {
        IOUtils.write(getDummyCustomer(), "customer.xml");

        IOUtils.write(IOUtils.read(new FileInputStream("customer.xml"), Customer.class), System.out);
    }

    public static class IOUtils {
        private static class StringOutputStream extends OutputStream {
            private StringBuilder stringBuf = new StringBuilder(new String("".getBytes(), Charset.forName("UTF-8")));
            @Override
            public void write(int b) throws IOException {
                this.stringBuf.append((char) b);
            }

            //Netbeans IDE automatically overrides this toString()
            public String toString(){
                return this.stringBuf.toString();
            }
        };

        private static String convertStreamToString(java.io.InputStream is) {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }

        private static String write(Object message) throws Exception {
            IOUtils.StringOutputStream os = new IOUtils.StringOutputStream();
            write(message, os);
            return os.toString();
        }

        private static void write(Object message, OutputStream os) throws Exception {
            IBindingFactory jc = BindingDirectory.getFactory(message.getClass());
            IMarshallingContext marshaller = jc.createMarshallingContext();
            marshaller.setIndent(4);
            marshaller.marshalDocument(message, "utf-8", null, os);
            marshaller.getXmlWriter().flush();
        }

        private static void write(Object message, String fileName) throws Exception {
            write(message, new FileOutputStream(fileName));
        }

        private static <T> T read(InputStream inStream, Class<T> clazz) throws Exception {
            IBindingFactory jc = BindingDirectory.getFactory(clazz);
            return (T)jc.createUnmarshallingContext().unmarshalDocument(inStream, null);
        }
    }

    public static Customer getDummyCustomer() {
        Customer c = new Customer();

        c.setName(new Name());
        c.setAddress(new Address());

        c.getAddress().setCity("1");
        c.getAddress().setState("2");
        c.getAddress().setStreet("3");
        c.getAddress().setZip(4);

        c.getName().setFirstName("Имя");
        c.getName().setLastName("lastName");

        c.setOrders(new Order[]{new Order()});
        c.getOrders()[0].setAmount(new BigDecimal(1337));
        c.getOrders()[0].setDate(new Date());
        return c;
    }
}
