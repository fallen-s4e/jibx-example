
import example.Customer;
import org.jibx.runtime.*;

import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: magzhan.karasayev
 * Date: 08.01.14
 * Time: 16:22
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        Customer message = read();

        /*
        model.setGroupId(groupId);
        model.setArtifactId(artifactId);
        */

        write(new Customer(), "customer.xml");
    }

    private static void write(Customer message, String fileName) throws Exception {
        IBindingFactory jc = BindingDirectory.getFactory(Customer.class);
        IMarshallingContext marshaller = jc.createMarshallingContext();
        marshaller.setIndent(4);
        marshaller.marshalDocument(message, "utf-8", null, new FileOutputStream(fileName));
        marshaller.getXmlWriter().flush();
//
//        IMarshallingContext mctx = jc.createMarshallingContext();
//        mctx.setOutput(new FileOutputStream(fileName), null);
//        ((IMarshallable)obj).marshal(mctx);
//        mctx.getXmlWriter().flush();
    }

    private static Customer read() throws JiBXException {
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("xml/customer.xml");
        IBindingFactory jc = BindingDirectory.getFactory(Customer.class);
        return (Customer)jc.createUnmarshallingContext().unmarshalDocument(inStream, null);
    }
}
