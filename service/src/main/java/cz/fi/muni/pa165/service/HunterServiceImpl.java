package cz.fi.muni.pa165.service;

import cz.fi.muni.pa165.dao.HunterDao;
import cz.fi.muni.pa165.entity.Hunter;
import cz.fi.muni.pa165.exception.HunterAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
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
    public void registerHunter(Hunter hunter, String unencryptedPassword) throws HunterAuthenticationException {
        if (hunter == null) {
            throw new IllegalArgumentException("Hunter is null");
        }

        if (StringUtils.isEmpty(unencryptedPassword)) {
            throw new IllegalArgumentException("Password is empty");
        }

        hunter.setPasswordHash(createHash(unencryptedPassword));
        hunterDao.create(hunter);
    }

    @Override
    public boolean authenticate(Hunter hunter, String password) throws HunterAuthenticationException {
        if (hunter == null || StringUtils.isEmpty(password)) {
            return false;
        }

        return verifyPassword(password, hunter.getPasswordHash());
    }

    @Override
    public Hunter update(Hunter hunter) {
        return hunterDao.update(hunter);
    }

    @Override
    public void changePassword(Long hunterId, String oldUnencryptedPassword, String newUnencryptedPassword)
            throws HunterAuthenticationException {
        if (StringUtils.isEmpty(oldUnencryptedPassword)) {
            throw new IllegalArgumentException("old Password is empty");
        }

        if (StringUtils.isEmpty(newUnencryptedPassword)) {
            throw new IllegalArgumentException("new Password is empty");
        }

        Hunter hunter = hunterDao.findById(hunterId);

        if (hunter == null) {
            throw new IllegalArgumentException("Hunter id null");
        }

        if (verifyPassword(oldUnencryptedPassword, hunter.getPasswordHash())) {
            hunter.setPasswordHash(createHash(newUnencryptedPassword));
            hunterDao.update(hunter);
        }
    }

    @Override
    public void delete(Hunter hunter) {
        hunterDao.delete(hunter);
    }

    @Override
    public Hunter findById(Long id) {
        return hunterDao.findById(id);
    }

    @Override
    public Hunter findByEmail(String email) {
        return hunterDao.findByEmail(email);
    }

    @Override
    public List<Hunter> findAll() {
        return hunterDao.findAll();
    }

    private static String createHash(String unencryptedPassword) throws HunterAuthenticationException {

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

        return PBKDF2_ITERATIONS + ":" + toBase64(salt) + ":" + toBase64(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes)
            throws HunterAuthenticationException {

        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException ex) {
            throw new HunterAuthenticationException("Authentication error", ex);
        }
    }

    private static byte[] fromBase64(String hex)
            throws IllegalArgumentException {

        return DatatypeConverter.parseBase64Binary(hex);
    }

    private static String toBase64(byte[] array) {
        return DatatypeConverter.printBase64Binary(array);
    }

    private static boolean verifyPassword(String password, String correctHash) throws HunterAuthenticationException {
        if (StringUtils.isEmpty(password)) {
            return false;
        }
        if (correctHash == null) {
            throw new IllegalArgumentException("password hash is null");
        }

        String[] params = correctHash.split(":");

        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromBase64(params[1]);
        byte[] hash = fromBase64(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }
}
