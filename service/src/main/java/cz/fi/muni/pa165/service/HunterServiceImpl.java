package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.HunterDao;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.List;


import java.util.List;

/**
 * Created by Erik Macej 433744 , on 19.11.16.
 *
 * @author Erik Macej 433744
 */
@Service
public class HunterServiceImpl implements HunterService {

    @Inject
    HunterDao hunterDao;

    @Override
    public void registerHunter(Hunter hunter, String unencryptedPassword) throws InvalidKeySpecException,
            NoSuchAlgorithmException {

        hunter.setPasswordHash(createHash(unencryptedPassword));
        hunterDao.create(hunter);
    }

    @Override
    public boolean authenticate(Hunter hunter, String password) throws InvalidKeySpecException,
            NoSuchAlgorithmException {

        return verifyPassword(password, hunter.getPasswordHash());
    }

    @Override
    public boolean isAdmin(Hunter hunter) {
        return findById(hunter.getId()).getType().equals(Role.ADMIN);
    }

    @Override
    public Hunter update(Hunter hunter) { return hunterDao.update(hunter); }

    @Override
    public void changePassword(Hunter hunter,String oldUnencryptedPassword, String newUnencryptedPassword)
            throws InvalidKeySpecException, NoSuchAlgorithmException {

        if(verifyPassword(oldUnencryptedPassword,hunter.getPasswordHash())){
            hunter.setPasswordHash(createHash(newUnencryptedPassword));
            hunterDao.update(hunter);
        }
    }

    @Override
    public void delete(Hunter hunter) { hunterDao.delete(hunter); }

    @Override
    public Hunter findById(Long id) { return hunterDao.findById(id); }

    @Override
    public Hunter findByEmail(String email) { return hunterDao.findByEmail(email); }

    @Override
    public List<Hunter> findAll() { return hunterDao.findAll(); }

    private static String createHash(String unencryptedPassword) throws InvalidKeySpecException,
            NoSuchAlgorithmException {

        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 18;
        final int PBKDF2_ITERATIONS = 1000;

        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(unencryptedPassword.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        int hashSize = hash.length;

        return PBKDF2_ITERATIONS + ":" + hashSize + ":" + toBase64(salt) + ":" + toBase64(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();

        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException("Hash algorithm not supported.");

        } catch (InvalidKeySpecException ex) {
            throw new InvalidKeySpecException("Invalid key spec.");
        }
    }

    private static byte[] fromBase64(String hex)
            throws IllegalArgumentException {

        return DatatypeConverter.parseBase64Binary(hex);
    }

    private static String toBase64(byte[] array) {
        return DatatypeConverter.printBase64Binary(array);
    }

    public static boolean verifyPassword(String password, String correctHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
        if(password==null) return false;
        if(correctHash==null) throw new IllegalArgumentException("password hash is null");

        String[] params = correctHash.split(":");

        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromBase64(params[1]);
        byte[] hash = fromBase64(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);

    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }


}
