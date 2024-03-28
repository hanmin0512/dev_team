package com.example.hackingproject.login.service;

import com.example.hackingproject.common.JwtTokenUtil;
import com.example.hackingproject.login.vo.UserVO;
import com.example.hackingproject.dao.LoginDAO;
import com.example.hackingproject.login.dto.LoginReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@Service("LoginService")
public class LoginService {
    @Autowired
    private LoginDAO loginDAO;

    @Value("${rsa_instance}")
    private String RSA_INSTANCE; // rsa transformation

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Map<String, Object> getUserInfo(LoginReq loginReq, PrivateKey privateKey){

        String userId = "";
        String userPw = "";
        try{
            userId = decryptRsa(privateKey, loginReq.getId());
            userPw = decryptRsa(privateKey, loginReq.getPass());
        }catch (Exception e){

        }
        // 복호화된 데이터
        LoginReq decryptionData = new LoginReq();
        decryptionData.setId(userId);
        decryptionData.setPass(userPw);

        loginReq.setId(userId);
        loginReq.setPass(SHA256Encrypt(userPw));

        UserVO user = loginDAO.getUserInfo(loginReq);

        Map<String, Object> result = new HashMap<String,Object>();

        if(user == null){
        }else{
            user.setAuth("user");
            loginDAO.updateUserLogin(user);
            // JWT 셋팅
            result.put("jwtToken",jwtTokenUtil.generateTokenForUser(user));
        }

        //session.removeAttribute(RSA_WEB_KEY);
        result.put("userData",user);
        result.put("decryptionData",decryptionData);
        result.put("sha256pass",loginReq.getPass());

        return result;
    }


    /**
     * 복호화
     *
     * @param privateKey
     * @param securedValue
     * @return
     * @throws Exception
     */
    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA_INSTANCE);
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedValue = new String(decryptedBytes, StandardCharsets.UTF_8); // 문자 인코딩 주의.
        return decryptedValue;
    }

    /**
     * 16진 문자열을 byte 배열로 변환한다.
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }

    /**
     * SHA-256 암호화
     *
     * @param pass
     * @return String
     */
    public static String SHA256Encrypt(String pass) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(pass.getBytes());
            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
