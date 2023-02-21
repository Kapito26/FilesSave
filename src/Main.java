import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        //Создать три экземпляра класса GameProgress
        GameProgress gameProgress1 = new GameProgress(94, 10, 2, 254.32);
        GameProgress gameProgress2 = new GameProgress(85, 8, 1, 154.08);
        GameProgress gameProgress3 = new GameProgress(76, 9, 2, 202.12);

        //Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи
        String CurrentDir = "D:/Games/savegames/";

        saveGame(gameProgress1, CurrentDir + "save1.dat");
        saveGame(gameProgress2, CurrentDir + "save2.dat");
        saveGame(gameProgress3, CurrentDir + "save3.dat");

        //Созданные файлы сохранений из папки savegames запаковать в архив zip
        String Files[] = {"save1.dat", "save2.dat", "save3.dat"};
        zipFiles(CurrentDir, "zip.zip", Files);

        // Удалить файлы сохранений, лежащие вне архива
        for (int i = 0; i < Files.length; i++) {
            File newF = new File(CurrentDir, Files[i]);
            if (newF.exists()) {
                newF.delete();
            }
        }
    }

    public static void saveGame(GameProgress gameProgress, String s) {
        try (FileOutputStream fos = new FileOutputStream(s);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void zipFiles(String d, String z, String[] f) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(d + z))) {
            for (int i = 0; i < f.length; i++) {
                try (FileInputStream fis = new FileInputStream(d + f[i])) {
                    ZipEntry entry = new ZipEntry(f[i]);
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}