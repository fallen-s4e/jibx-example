
import example.Address;
import example.Customer;
import org.jibx.runtime.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: magzhan.karasayev
 * Date: 08.01.14
 * Time: 16:22
 */
public class Main {
    public static void main(String[] args) throws Exception {
        write(getDummyCustomer(), "customer.xml");

        write(read("customer.xml"), System.out);
    }

    private static void write(Customer message, OutputStream os) throws Exception {
        IBindingFactory jc = BindingDirectory.getFactory(Customer.class);
        IMarshallingContext marshaller = jc.createMarshallingContext();
        marshaller.setIndent(4);
        marshaller.marshalDocument(message, "utf-8", null, os);
        marshaller.getXmlWriter().flush();
    }

    private static void write(Customer message, String fileName) throws Exception {
        write(message, new FileOutputStream(fileName));
    }

    private static Customer read(String fileName) throws Exception {
        InputStream inStream = new FileInputStream(fileName);
        IBindingFactory jc = BindingDirectory.getFactory(Customer.class);
        return (Customer)jc.createUnmarshallingContext().unmarshalDocument(inStream, null);
    }

    public static Customer getDummyCustomer() {
        Customer c = new Customer();
        c.setAddress(new Address());
        c.getAddress().setCity("1");
        c.getAddress().setState("2");
        c.getAddress().setStreet("3");
        c.getAddress().setZip(4);
        return c;
    }
}
