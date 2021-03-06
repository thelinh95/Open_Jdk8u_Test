package test.javax.rmi.PortableRemoteObject;
// Stub class generated by rmic, do not edit.
// Contents subject to change without notice.

import java.io.Serializable;
import java.net.InetAddress;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import javax.rmi.CORBA.Stub;
import javax.rmi.CORBA.Util;
import org.omg.CORBA.ORB;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.CORBA.portable.ServantObject;


public class _HelloInterface_Stub extends Stub implements HelloInterface {

    private static final String[] _type_ids = {
        "RMI:HelloInterface:0000000000000000"
    };

    public String[] _ids() {
        return (String[]) _type_ids.clone();
    }

    public String sayHello(String arg0) throws test.java.rmi.RemoteException {
        if (!Util.isLocal(this)) {
            try {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    org.omg.CORBA_2_3.portable.OutputStream out =
                        (org.omg.CORBA_2_3.portable.OutputStream)
                        _request("sayHello", true);
                    out.write_value(arg0,String.class);
                    in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                    return (String) in.read_value(String.class);
                } catch (ApplicationException ex) {
                    in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                    String $_id = in.read_string();
                    throw new UnexpectedException($_id);
                } catch (RemarshalException ex) {
                    return sayHello(arg0);
                } finally {
                    _releaseReply(in);
                }
            } catch (SystemException ex) {
                throw Util.mapSystemException(ex);
            }
        } else {
            ServantObject so = _servant_preinvoke("sayHello",HelloInterface.class);
            if (so == null) {
                return sayHello(arg0);
            }
            try {
                return ((HelloInterface)so.servant).sayHello(arg0);
            } catch (Throwable ex) {
                Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                throw Util.wrapException(exCopy);
            } finally {
                _servant_postinvoke(so);
            }
        }
    }

    public String sayHelloToTest(Test arg0) throws test.java.rmi.RemoteException {
        if (!Util.isLocal(this)) {
            try {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    org.omg.CORBA_2_3.portable.OutputStream out =
                        (org.omg.CORBA_2_3.portable.OutputStream)
                        _request("sayHelloToTest", true);
                    out.write_value(arg0,Test.class);
                    in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                    return (String) in.read_value(String.class);
                } catch (ApplicationException ex) {
                    in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                    String $_id = in.read_string();
                    throw new UnexpectedException($_id);
                } catch (RemarshalException ex) {
                    return sayHelloToTest(arg0);
                } finally {
                    _releaseReply(in);
                }
            } catch (SystemException ex) {
                throw Util.mapSystemException(ex);
            }
        } else {
            ServantObject so = _servant_preinvoke("sayHelloToTest",HelloInterface.class);
            if (so == null) {
                return sayHelloToTest(arg0);
            }
            try {
                Test arg0Copy = (Test) Util.copyObject(arg0,_orb());
                return ((HelloInterface)so.servant).sayHelloToTest(arg0Copy);
            } catch (Throwable ex) {
                Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                throw Util.wrapException(exCopy);
            } finally {
                _servant_postinvoke(so);
            }
        }
    }

    public String sayHelloWithInetAddress(InetAddress arg0) throws test.java.rmi.RemoteException {
        if (!Util.isLocal(this)) {
            try {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    org.omg.CORBA_2_3.portable.OutputStream out =
                        (org.omg.CORBA_2_3.portable.OutputStream)
                        _request("sayHelloWithInetAddress", true);
                    out.write_value(arg0,InetAddress.class);
                    in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                    return (String) in.read_value(String.class);
                } catch (ApplicationException ex) {
                    in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                    String $_id = in.read_string();
                    throw new UnexpectedException($_id);
                } catch (RemarshalException ex) {
                    return sayHelloWithInetAddress(arg0);
                } finally {
                    _releaseReply(in);
                }
            } catch (SystemException ex) {
                throw Util.mapSystemException(ex);
            }
        } else {
            ServantObject so = _servant_preinvoke("sayHelloWithInetAddress",HelloInterface.class);
            if (so == null) {
                return sayHelloWithInetAddress(arg0);
            }
            try {
                InetAddress arg0Copy = (InetAddress) Util.copyObject(arg0,_orb());
                return ((HelloInterface)so.servant).sayHelloWithInetAddress(arg0Copy);
            } catch (Throwable ex) {
                Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                throw Util.wrapException(exCopy);
            } finally {
                _servant_postinvoke(so);
            }
        }
    }

    public String sayHelloWithHashMap(ConcurrentHashMap arg0) throws test.java.rmi.RemoteException {
        if (!Util.isLocal(this)) {
            try {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    org.omg.CORBA_2_3.portable.OutputStream out =
                        (org.omg.CORBA_2_3.portable.OutputStream)
                        _request("sayHelloWithHashMap", true);
                    out.write_value(arg0,ConcurrentHashMap.class);
                    in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                    return (String) in.read_value(String.class);
                } catch (ApplicationException ex) {
                    in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                    String $_id = in.read_string();
                    throw new UnexpectedException($_id);
                } catch (RemarshalException ex) {
                    return sayHelloWithHashMap(arg0);
                } finally {
                    _releaseReply(in);
                }
            } catch (SystemException ex) {
                throw Util.mapSystemException(ex);
            }
        } else {
            ServantObject so = _servant_preinvoke("sayHelloWithHashMap",HelloInterface.class);
            if (so == null) {
                return sayHelloWithHashMap(arg0);
            }
            try {
                ConcurrentHashMap arg0Copy = (ConcurrentHashMap) Util.copyObject(arg0,_orb());
                return ((HelloInterface)so.servant).sayHelloWithHashMap(arg0Copy);
            } catch (Throwable ex) {
                Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                throw Util.wrapException(exCopy);
            } finally {
                _servant_postinvoke(so);
            }
        }
    }

    public String sayHelloWithHashMap2(HashMap arg0) throws test.java.rmi.RemoteException {
        if (!Util.isLocal(this)) {
            try {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    org.omg.CORBA_2_3.portable.OutputStream out =
                        (org.omg.CORBA_2_3.portable.OutputStream)
                        _request("sayHelloWithHashMap2", true);
                    out.write_value(arg0,HashMap.class);
                    in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                    return (String) in.read_value(String.class);
                } catch (ApplicationException ex) {
                    in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                    String $_id = in.read_string();
                    throw new UnexpectedException($_id);
                } catch (RemarshalException ex) {
                    return sayHelloWithHashMap2(arg0);
                } finally {
                    _releaseReply(in);
                }
            } catch (SystemException ex) {
                throw Util.mapSystemException(ex);
            }
        } else {
            ServantObject so = _servant_preinvoke("sayHelloWithHashMap2",HelloInterface.class);
            if (so == null) {
                return sayHelloWithHashMap2(arg0);
            }
            try {
                HashMap arg0Copy = (HashMap) Util.copyObject(arg0,_orb());
                return ((HelloInterface)so.servant).sayHelloWithHashMap2(arg0Copy);
            } catch (Throwable ex) {
                Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                throw Util.wrapException(exCopy);
            } finally {
                _servant_postinvoke(so);
            }
        }
    }

    public String sayHelloWithReentrantLock(ReentrantLock arg0) throws test.java.rmi.RemoteException {
        if (!Util.isLocal(this)) {
            try {
                org.omg.CORBA_2_3.portable.InputStream in = null;
                try {
                    org.omg.CORBA_2_3.portable.OutputStream out =
                        (org.omg.CORBA_2_3.portable.OutputStream)
                        _request("sayHelloWithReentrantLock", true);
                    out.write_value(arg0,ReentrantLock.class);
                    in = (org.omg.CORBA_2_3.portable.InputStream)_invoke(out);
                    return (String) in.read_value(String.class);
                } catch (ApplicationException ex) {
                    in = (org.omg.CORBA_2_3.portable.InputStream) ex.getInputStream();
                    String $_id = in.read_string();
                    throw new UnexpectedException($_id);
                } catch (RemarshalException ex) {
                    return sayHelloWithReentrantLock(arg0);
                } finally {
                    _releaseReply(in);
                }
            } catch (SystemException ex) {
                throw Util.mapSystemException(ex);
            }
        } else {
            ServantObject so = _servant_preinvoke("sayHelloWithReentrantLock",HelloInterface.class);
            if (so == null) {
                return sayHelloWithReentrantLock(arg0);
            }
            try {
                ReentrantLock arg0Copy = (ReentrantLock) Util.copyObject(arg0,_orb());
                return ((HelloInterface)so.servant).sayHelloWithReentrantLock(arg0Copy);
            } catch (Throwable ex) {
                Throwable exCopy = (Throwable)Util.copyObject(ex,_orb());
                throw Util.wrapException(exCopy);
            } finally {
                _servant_postinvoke(so);
            }
        }
    }
}
