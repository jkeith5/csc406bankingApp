package BankingApp;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ModuleLoader {

    private static final Class<?>[] params = new Class[]{URL.class};

    public static void addModule(String s) throws IOException {
        File f = new File(s);
        addModule(f);
    }

    public static void addModule(File f) throws IOException {
        loadURLFromModuleFile(f.toURI().toURL());
    }

    public static void loadURLFromModuleFile(URL url) throws IOException {
        URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
        Class<?> systemClass = URLClassLoader.class;
        try {
            Method method = systemClass.getDeclaredMethod("addURL", params);
            method.setAccessible(true);
            method.invoke(classLoader,new Object[]{ url });
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Unable to Load File.");
        }
    }


}
