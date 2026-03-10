import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.dinamonetworks.Dinamo;
import br.com.trueaccess.TacException;
import br.com.trueaccess.TacNDJavaLib;

public class ChaveSessao {
    static String ip = "10.50.137.46";
    static String usuario = "utfpr1";
    static String senha = "segcomp20241";
                       
    static String pem = "MFQCAQAwEAYHKoZIzj0CAQYFK4EEABwEPTA7AgEBBBBJcQFE41PHWTPNOKpBgmZKoSQDIgAE/3zWGOxiRg33LLwfXHkZD8MWYCAano63tj8Q+CDOLLw=";
    static String chavepub = "MDYwEAYHKoZIzj0CAQYFK4EEABwDIgAEidS5wkglwUx7xvbFet5uIyNrdYyKT27nFYPS39iXFeY=";
    static String ctrr = "ghrhsdfhwt";
    static String teste = "ola";

    public static  void main(String[] args) throws TacException, IOException, NoSuchAlgorithmException{
        Dinamo api = new Dinamo();
        Scanner scanner = new Scanner(System.in);

        api.openSession(ip, usuario, senha);
        api.deleteKeyIfExists(ctrr);
        try{
            api.deleteKey(ctrr);
        }catch(TacException e) {}
       
        try{
            api.deleteKey(teste);
        }catch(TacException e) {}

        byte[] pkcs8 = Base64.getMimeDecoder().decode(pem);
        byte[] cliente = Base64.getMimeDecoder().decode(chavepub);
       
        byte[] pbKDFData = {(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0x11,(byte)0x12,(byte)0x13,(byte)0x14,(byte)0x15,(byte)0x16,(byte)0x17,(byte)0x18};
       
        api.PKCS8ImportKey(ctrr, "", TacNDJavaLib.ALG_ECC_SECP128R1, pkcs8, false);
       
        byte[] pbkey = api.genEcdhKeyX963Sha256(ctrr,null,TacNDJavaLib.ALG_AES_256,false,false,cliente,pbKDFData);
       
        api.importKey( teste,
                            TacNDJavaLib.PLAINTEXTKEY_BLOB,
                            TacNDJavaLib.ALG_AES_256,
                            TacNDJavaLib.EXPORTABLE_KEY,
                            pbkey,
                            TacNDJavaLib.ALG_AES_256_LEN);
                           
       
        String ctB64 = scanner.nextLine();
        byte[] ctBytes = Base64.getDecoder().decode(ctB64);
       
        byte[] pbDecryptedBuffer = api.decrypt(teste, ctBytes);
       
        String ola = Base64.getMimeEncoder().encodeToString(pbDecryptedBuffer);
        System.out.println(ola);

        api.deleteKeyIfExists(ctrr);
        api.deleteKeyIfExists(teste);

        api.closeSession();
    }
}
