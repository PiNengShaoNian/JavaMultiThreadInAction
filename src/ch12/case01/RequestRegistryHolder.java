package ch12.case01;

import util.Debug;
import util.Tools;

public class RequestRegistryHolder {
    static final RequestRegistryHolder INSTANCE = new RequestRegistryHolder();
    private final RequestRegistry rr;

    private RequestRegistryHolder() {
        String implClassName = System.getProperty("x.rr.impl");
        if (implClassName == null) {
            implClassName = "FineRequestRegistry";
        }
        implClassName = RequestRegistryHolder.class.getPackage().getName() + "." + implClassName;
        Debug.info("Using %s as implementation.", implClassName);
        RequestRegistry rrInstance = null;
        try {
            rrInstance = (RequestRegistry) Tools.newInstanceOf(implClassName);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        rr = rrInstance;
    }

    RequestRegistry getRegistry() {
        return rr;
    }
}
