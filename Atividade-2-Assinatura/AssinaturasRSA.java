import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Base64;
import com.dinamonetworks.Dinamo;
import br.com.trueaccess.TacException;
import br.com.trueaccess.TacNDJavaLib;

public class AssinaturasRSA{
static String ip = "10.50.137.46";
static String usuario = "utfpr1";
static String senha = "segcomp20241";

static String pem = "MIIBCgKCAQEA27eTN0jetmncZjQfjpZPa8dq4kZ67qyVsLJA6JUrntbolctfD6AqVrR3FG78f+EnWdjpwDNm8RyVgaceagd0ipi7+zBSArA0Laq0IhWWVXJIr0glwk3FeuhVAyQIXdownTn6dv15e+RqBx6g/T5xCGBG/Uwz2j7uw+RXf3nbfXxLBBnGu+4GHUOE82tTHmpv8YBKe9D5WvKaMY4b7M9PzjqlSJk159ON3qOnnIyopO67a46WZbCal6pdQg0jOyx+p3HX8ZCtFJhe95eYOFcueXAbuhEkk9zYE7mOFCwswRGKP2s5X9k8w9AEVkw7I5P2iNdDdO5Rfn4F9IvXtn4+EwIDAQABMIIEpAIBAAKCAQEA27eTN0jetmncZjQfjpZPa8dq4kZ67qyVsLJA6JUrntbolctfD6AqVrR3FG78f+EnWdjpwDNm8RyVgaceagd0ipi7+zBSArA0Laq0IhWWVXJIr0glwk3FeuhVAyQIXdownTn6dv15e+RqBx6g/T5xCGBG/Uwz2j7uw+RXf3nbfXxLBBnGu+4GHUOE82tTHmpv8YBKe9D5WvKaMY4b7M9PzjqlSJk159ON3qOnnIyopO67a46WZbCal6pdQg0jOyx+p3HX8ZCtFJhe95eYOFcueXAbuhEkk9zYE7mOFCwswRGKP2s5X9k8w9AEVkw7I5P2iNdDdO5Rfn4F9IvXtn4+EwIDAQABAoIBAF031HjHLN5dqYmfIahgM0/56moYFv3+MQAnkIxdBYW9ji5He4xomq3woPkFYZFr2EMxWXMXG9y+ENxSKAA6K4LF7DNVO+YyFeEuJIC1gfO8qgPb61E1p8xOmAreFcKj2K5MM+yZ/BnJPgbJQupOvKweFOBQnUtCGhD4ZvsJm0rwUKPEhQj4yPx1ZiUGjnecLAu/UlY8OZps2cpspzzH9c8VHeLVT0lxCQWO54+jgIl7ELZeSmbuQgc1Ec34jDHM8PtV2x12hshqrNJIH2NjQlXZDIQmIFQNrPQWcTOVuljbClQV4XEENn0Exy+TmAg93hZ1oHsMJCoegVTAx6FkR5kCgYEA8ourIj2YYtUL7vutEHK9BYGM7jsOZonioUA1oSFmFfxVMiDT/hM06S8waowwXxzavwvmekdihFRtH0RFKtJehdKll4WJhLQkRGHWPuP8whHm+I8Ir7k11e981m0eBC6/rMxUvhDAbKIWBb5i+kKGmH87Rz4jVX8LpbXj9tVe8BUCgYEA5+e5g/hNDquHfNQrkgFvPCntnUuN/nMOEuUKf+s6gRTeV6LbTfULMHXQrdXNtNRvaMqA7VOUaQUXWvcfCgmcxZw7T68oyd4f4qvhUZA2BYitYcO3M1OO8PaotA/EVA9Y0uWvZUAAK6WfjNjeT1hD3EMeZLT/Fa7TBtL/YMWc14cCgYBznL3DQYcMvS7GCLNS3s5Y/Iq31/CiUeF0KY4msyvdGP1nOpyyRGd5K2QxtYctHyzp2ftPmA1OtIubVsh9g+9IbMM/+Yg2eEO9UMF2EuidfTkSi+OBF5Cg3FouP68KIVp+PJu8SXHflldW/4xgbCuKL0OLTwQ6sadUouH5+3UYjQKBgQCwjUQ5aoIP1xrxN/5ry9fJsAoMpIbX3vQqAmG5/XWu7BpSEdRwR0xbenaCwbE4KDshRnpdNDJauduMD3N8CtFlyImHIs0k0ozn3v9NP0NBd0q4kW4aD1VVshPB8fcAICb5eI/Nv6nhkNglPL4THhmbySWMehdxhIV4Po7aN9guZQKBgQDJo7e+xm4vWTGWGGO8BN5SVLQDZletBJ6IyGWZdwxx5kFLBhdwaB4LIESVvmeONV42DU8v2WpuBU35qNKSFr7DecfN48dCFoseb0Mbb1JO4/SQuehdD+EtvgE7kL9VqcFY12hEZNai3HnCYXncIm/dAbb2dEDDGjU1hkw+9q9M8g==";

static String keyip = "chaveTemp";

public static void main(String[] args) throws TacException, IOException {
Dinamo api = new Dinamo();
Scanner scanner = new Scanner(System.in);
api.openSession(ip, usuario, senha);

try {
api.deleteKey(keyip);
} catch(TacException e) {}
 
 byte[] pkcs8 = Base64.getMimeDecoder().decode(pem);
 
   api.importKey (
                   keyip,
                   TacNDJavaLib.PUBLICKEY_BLOB_HSM,
                   TacNDJavaLib.ALG_OBJ_PUBKEY_RSA_BLOB,
                   pkcs8,
                   false
                 );

 String mensagem = scanner.nextLine();
 byte[] mensagemBytes = mensagem.getBytes();
 
 String assinaturaB64 = scanner.nextLine();
 byte[] assinaturaBytes = Base64.getDecoder().decode(assinaturaB64);
 
 int resultado = 0;
 try {
     resultado = api.verifySignature(keyip, TacNDJavaLib.ALG_SHA2_256, 0, assinaturaBytes, mensagemBytes);
 }
 catch(TacException e){
     System.out.println("Invalid");
     resultado =  -1;
 }
 if(resultado == 0){
   System.out.println("Valid");
 }
 api.closeSession();
}
}


