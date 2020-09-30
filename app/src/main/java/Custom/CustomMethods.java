package Custom;

import android.graphics.Bitmap;

import com.example.egorgoshasigninup.User;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class CustomMethods {

    public static Bitmap returnUserOI(String path){
        // считываем из файла оригинальное изображение
//        String path = user.getOriginalImagePath();
        File f = new File(path);
        int size = (int) f.length();
        byte[] bytes = new byte[size];
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedInputStream buf = new BufferedInputStream(fis);
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap image = DbBitmapUtility.getImage(bytes);

        return image;

    }

    public static String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));

            return hexString.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean checkPassAndLogin(String pass, String email) {
        boolean answer;
        String passLetters = pass.replaceAll("//w", "");
        Pattern nums = Pattern.compile("1234567890");
        Pattern extraSymbols = Pattern.compile("!@№%^&*");
        boolean numsInPass = nums.split(pass).length > 0;  //цифра
        boolean extraSymbolsInPass = extraSymbols.split(pass).length > 0;      //спец.Символы
        boolean passLength = pass.length() >= 8;            //Длина
        boolean wordRegister = (passLetters != passLetters.toLowerCase() & passLetters != passLetters.toUpperCase()); //Регистр пароля
        boolean passCorrect = numsInPass & extraSymbolsInPass & passLength & wordRegister;

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        boolean emailCorrect = pat.matcher(email).matches();

        if (passCorrect & emailCorrect) {
            answer = true;
        } else {
            answer = false;
        }

        return answer;
    }
}
