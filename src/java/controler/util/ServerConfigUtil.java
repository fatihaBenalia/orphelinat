/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Younes
 */
public class ServerConfigUtil {

    private static String vmParam = "file.path";//chemin dont laquelle on va creer le dosqsier globale qui aura pour bute de contenir la totalitees des dossier d un abonnee

    public static String getEtudiantFilePath(boolean write) {
        String rootPath = "";
        if (write) {
            rootPath = System.getProperty(vmParam);
            
        } else {
            System.out.println("ana hna");
            rootPath = "\\myFile";
        }
        return rootPath;
    }
    public static void upload(UploadedFile uploadedFile, String destinationPath, String nameOfUploadeFile) {
        try {
            String fileSavePath = destinationPath + "\\" + nameOfUploadeFile;
            InputStream fileContent = null;
            fileContent = uploadedFile.getInputstream();
            Files.copy(fileContent, new File(fileSavePath).toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("ha path"+fileSavePath);
        } catch (IOException e) {
            JsfUtil.addErrorMessage(e, "Erreur Upload image");
            System.out.println("No update !!!!");
            e.printStackTrace();
        }
    }
    
    
}
