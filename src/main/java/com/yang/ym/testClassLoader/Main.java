package com.yang.ym.testClassLoader;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * @author qcy
 * @create 2021/09/03 22:41:09
 */
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "root");

        Enumeration<Driver> en = DriverManager.getDrivers();
        while (en.hasMoreElements()) {
            java.sql.Driver driver = en.nextElement();
            System.out.println(driver);
        }

    }


//    public Class loadClass(String name, boolean resolve)
//            throws ClassNotFoundException {
//
//        Class clazz = null;
//
//        //1.从自己的缓存中查找，缓存数据结构为ResourceEntry
//        clazz = findLoadedClass0(name);
//        if (clazz != null) {
//            if (resolve)
//                resolveClass(clazz);
//            return (clazz);
//        }
//
//        //2.从
//        clazz = findLoadedClass(name);
//        if (clazz != null) {
//            if (resolve)
//                resolveClass(clazz);
//            return (clazz);
//        }
//
//        // (0.2) Try loading the class with the system class loader, to prevent
//        //       the webapp from overriding J2SE classes
//        try {
//            clazz = system.loadClass(name);
//            if (clazz != null) {
//                if (resolve)
//                    resolveClass(clazz);
//                return (clazz);
//            }
//        } catch (ClassNotFoundException e) {
//            // Ignore
//        }
//
//        // (0.5) Permission to access this class when using a SecurityManager
//        if (securityManager != null) {
//            int i = name.lastIndexOf('.');
//            if (i >= 0) {
//                try {
//                    securityManager.checkPackageAccess(name.substring(0, i));
//                } catch (SecurityException se) {
//                    String error = "Security Violation, attempt to use " +
//                            "Restricted Class: " + name;
//                    log.info(error, se);
//                    throw new ClassNotFoundException(error, se);
//                }
//            }
//        }
//
//        boolean delegateLoad = delegate || filter(name);
//
//        // (1) Delegate to our parent if requested
//        if (delegateLoad) {
//            if (log.isDebugEnabled())
//                log.debug("  Delegating to parent classloader1 " + parent);
//            ClassLoader loader = parent;
//            if (loader == null)
//                loader = system;
//            try {
//                clazz = loader.loadClass(name);
//                if (clazz != null) {
//                    if (log.isDebugEnabled())
//                        log.debug("  Loading class from parent");
//                    if (resolve)
//                        resolveClass(clazz);
//                    return (clazz);
//                }
//            } catch (ClassNotFoundException e) {
//                ;
//            }
//        }
//
//        // (2) Search local repositories
//        if (log.isDebugEnabled())
//            log.debug("  Searching local repositories");
//        try {
//            clazz = findClass(name);
//            if (clazz != null) {
//                if (log.isDebugEnabled())
//                    log.debug("  Loading class from local repository");
//                if (resolve)
//                    resolveClass(clazz);
//                return (clazz);
//            }
//        } catch (ClassNotFoundException e) {
//            ;
//        }
//
//        // (3) Delegate to parent unconditionally
//        if (!delegateLoad) {
//            if (log.isDebugEnabled())
//                log.debug("  Delegating to parent classloader at end: " + parent);
//            ClassLoader loader = parent;
//            if (loader == null)
//                loader = system;
//            try {
//                clazz = loader.loadClass(name);
//                if (clazz != null) {
//                    if (log.isDebugEnabled())
//                        log.debug("  Loading class from parent");
//                    if (resolve)
//                        resolveClass(clazz);
//                    return (clazz);
//                }
//            } catch (ClassNotFoundException e) {
//                ;
//            }
//        }
//
//        throw new ClassNotFoundException(name);
//    }
}
