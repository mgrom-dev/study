import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Класс файла у которого нужно изменить размер
 * содержит сам файл, новую ширину и путь к конечному файлу
 */
class FileToResize{
    private File file;
    private String name;
    private int width;
    private String dstFolder;

    public FileToResize(File file, int width, String dstFolder) {
        this.file = file;
        this.width = width;
        this.dstFolder = dstFolder;
        this.name = file.getName().split("\\.")[0];
    }

    public File getFile() {
        return file;
    }

    public int getWidth() {
        return width;
    }

    public String getDstFolder() {
        return dstFolder;
    }

    public String getName() {
        return name;
    }
}

public class ResizeImg implements Runnable{
    //список изменяемых файлов
    private static Vector<FileToResize> files = new Vector<>();
    //время на работу преобразования всех файлов
    private static long timeStart;
    //максимальное количество потоков
    private static final int threadsMax = Runtime.getRuntime().availableProcessors();
    //количество потоков, которое запущено
    private static AtomicInteger threadsRunning = new AtomicInteger(0);

    //конструктор для добавления одного файла к ресайзу
    public ResizeImg(int size, String dstFolder, File file) {
        files.add(new FileToResize(file, size, dstFolder));
    }

    //конструктор для добавления массива файлов к ресайзу
    public ResizeImg(int size, String dstFolder, File ... files) {
        for (File file : files) {
            ResizeImg.files.add(new FileToResize(file, size, dstFolder));
        }
    }

    @Override
    public void run() {
        if (threadsRunning.get() == 0) {
            timeStart = System.currentTimeMillis();
        }
        while (threadsRunning.get() == threadsMax) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (files.size() > 0) {
            FileToResize fileToResize = files.get(0);
            files.remove(0);
            threadsRunning.incrementAndGet();
            new Thread(this).start();
            //делаем ресайз 3 способами
            try
            {
                BufferedImage image = ImageIO.read(fileToResize.getFile());
                if(image != null) {
                    int height = Math.round((float)image.getHeight() / (float)(image.getWidth() / fileToResize.getWidth()));
                    int width = fileToResize.getWidth();
                    BufferedImage imageBuff = Thumbnails.of(fileToResize.getFile())
                            .size(width, height)
                            .outputFormat("JPEG")
                            .outputQuality(1.0)
                            .asBufferedImage();
                    File newFile = new File(fileToResize.getDstFolder() + "/" + fileToResize.getName() + "_thumbnails.jpg");
                    ImageIO.write(imageBuff, "jpg", newFile);
                    imageBuff = resizeFix(image, width, height);
                    newFile = new File(fileToResize.getDstFolder() + "/" + fileToResize.getName() + "_fix.jpg");
                    ImageIO.write(imageBuff, "jpg", newFile);
                    imageBuff = resizeGraphic(image, width, height);
                    newFile = new File(fileToResize.getDstFolder() + "/" + fileToResize.getName() + "_graphic.jpg");
                    ImageIO.write(imageBuff, "jpg", newFile);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            threadsRunning.decrementAndGet();
        }
        if (threadsRunning.get() == 0) {
            System.out.println("Duration: " + (System.currentTimeMillis() - timeStart));
        }
    }

    private static BufferedImage resizeFix(BufferedImage image, int areaWidth, int areaHeight) {
        BufferedImage newImage = new BufferedImage(
                areaWidth, areaHeight, BufferedImage.TYPE_INT_RGB
        );
        int widthStep = image.getWidth() / areaWidth;
        int heightStep = image.getHeight() / areaHeight;
        for (int x = 0; x < areaWidth; x++)
        {
            for (int y = 0; y < areaHeight; y++) {
                int rgb = image.getRGB(x * widthStep, y * heightStep);
                newImage.setRGB(x, y, rgb);
            }
        }
        return newImage;
    }

    private static BufferedImage resizeGraphic(BufferedImage image, int areaWidth, int areaHeight) {
        float scaleX = (float) areaWidth / image.getWidth();
        float scaleY = (float) areaHeight / image.getHeight();
        float scale = Math.min(scaleX, scaleY);
        int w = Math.round(image.getWidth() * scale);
        int h = Math.round(image.getHeight() * scale);

        int type = image.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;

        boolean scaleDown = scale < 1;

        if (scaleDown) {
            // multi-pass bilinear div 2
            int currentW = image.getWidth();
            int currentH = image.getHeight();
            BufferedImage resized = image;
            while (currentW > w || currentH > h) {
                currentW = Math.max(w, currentW / 2);
                currentH = Math.max(h, currentH / 2);

                BufferedImage temp = new BufferedImage(currentW, currentH, type);
                Graphics2D g2 = temp.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.drawImage(resized, 0, 0, currentW, currentH, null);
                g2.dispose();
                resized = temp;
            }
            return resized;
        } else {
            Object hint = scale > 2 ? RenderingHints.VALUE_INTERPOLATION_BICUBIC : RenderingHints.VALUE_INTERPOLATION_BILINEAR;

            BufferedImage resized = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resized.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, hint);
            g2.drawImage(image, 0, 0, w, h, null);
            g2.dispose();
            return resized;
        }
    }
}
