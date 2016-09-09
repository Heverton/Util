package br.com.util.utilitario;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Serializa ou deserializa arquivos e objetos.
 *
 * @author Herberts Cruz
 * @version 1.1
 */
public class Serialize {

    /**
     * Insere no arquivo enviado por parametro os dados transformados em byte[].
     * 
     * @param arquivo
     * @param bytes
     * @return File
     * @throws IOException 
     */
    public static File serializeFile(File arquivo, byte[] bytes) throws IOException {
        try (BufferedOutputStream  bos = new BufferedOutputStream(new FileOutputStream(arquivo))) {
            bos.write(bytes);
            return arquivo;
        }
    }

    /**
     * Retorna o objeto que estava serializado e salvo no arquivo.
     * 
     * @param arquivo
     * @return Object
     * @throws IOException 
     * @throws ClassNotFoundException 
     */
    public static Object deserializeFile(File arquivo) throws IOException, ClassNotFoundException {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(arquivo))) {
            return is.readObject();
        }
    }

    /**
     * Serializa o objeto enviado e retorna-o em binário.
     * 
     * @param objeto
     * @return byte[]
     * @throws IOException 
     */
    public static byte[] serializeObject(Object objeto) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(); 
                ObjectOutputStream os = new ObjectOutputStream(out)) {
            os.writeObject(objeto);
            os.flush();
            return out.toByteArray();
        }
    }
    

    /**
     * Deserializa o objeto em binário enviado para sua forma normal.
     * 
     * @param data
     * @return Object
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public static Object deserializeObject(byte[] data) throws IOException, ClassNotFoundException {
        try (ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(data))) {
            return is.readObject();
        }
    }
}
