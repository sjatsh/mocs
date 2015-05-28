package smtcl.mocs.common.authority.sso;

import smtcl.mocs.utils.authority.ByteUtil;
import org.dreamwork.misc.AlgorithmUtil;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: seth.yang
 * Date: 12-9-29
 * Time: 上午11:46
 */
public class SsoToken implements Serializable {
    private Serializable userData;
    private long timestamp;
    private String token;

    public SsoToken (Serializable userData, long expire) {
        this.userData = userData;
        timestamp = System.currentTimeMillis () + expire * 1000;
    }

    public Serializable getUserData () {
        return userData;
    }

    public long getTimestamp () {
        return timestamp;
    }

    public String getToken () {
        if (token != null && token.trim ().length () > 0)
            return token;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream ();
            ObjectOutputStream oos = new ObjectOutputStream (baos);
            oos.writeObject (userData);
            oos.writeLong (timestamp);
            oos.writeDouble (Math.random ());
            oos.flush ();
            byte[] fingerPrint = AlgorithmUtil.md5 (baos.toByteArray ());
            return token = ByteUtil.bytes2Hex (fingerPrint, 'x');
        } catch (Exception ex) {
            byte[] buff = new byte[16];
            byte[] part1 = ByteUtil.long2Bytes (System.currentTimeMillis ());
            byte[] part2 = ByteUtil.double2Bytes (Math.random ());
            System.arraycopy (part1, 0, buff, 0, 8);
            System.arraycopy (part2, 0, buff, 8, 8);
            return token = ByteUtil.bytes2Hex (buff, 'x');
        }
    }
}