import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class FileAccess
{
    private final FileSystem HDFS;
    /**
     * Initializes the class, using rootPath as "/" directory
     *
     * @param rootPath - the path to the root of HDFS,
     * for example, hdfs://localhost:32771
     */
    public FileAccess(String rootPath)
    {
        FileSystem hdfs = null;
        try{
            hdfs = FileSystem.get(new URI("hdfs://" + rootPath + ":8020"), new Configuration() {{
                set("dfs.client.use.datanode.hostname", "true");
                setBoolean("dfs.support.append", true);
                setBoolean("dfs.client.block.write.replace-datanode-on-failure.best-effort", true);
            }});
        } catch (Exception ex){
            ex.printStackTrace();
        }
        HDFS = hdfs;
    }

    /**
     * Creates empty file or directory
     */
    public void create(String path)
    {
        try {
            FSDataOutputStream fsdos = HDFS.create(new Path(path), true);
            fsdos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Appends content to the file
     */
    public void append(String path, String content)
    {
        try {
            Path uri = new Path(path);
            if (!HDFS.exists(uri)){
                create(path);
            }
            FSDataOutputStream fs_append = HDFS.append(uri);
            PrintWriter writer = new PrintWriter(fs_append);
            writer.append(content);
            writer.flush();
            fs_append.hflush();
            writer.close();
            fs_append.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /**
     * Returns content of the file
     */
    public String read(String path)
    {
        StringBuilder str = new StringBuilder();
        try{
            Path uri = new Path(path);
            if (HDFS.exists(uri)) {
                FSDataInputStream fs_input = HDFS.open(uri);
                str.append(new String(fs_input.readAllBytes(), StandardCharsets.UTF_8));
                fs_input.close();
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return str.toString();
    }

    /**
     * Deletes file or directory
     */
    public void delete(String path)
    {
        try {
            Path uri = new Path(path);
            if (HDFS.exists(uri)) {
                HDFS.delete(uri, !uri.isRoot());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Checks, is the "path" is directory or file
     */
    public boolean isDirectory(String path)
    {
        boolean isDirectory = false;
        try {
            isDirectory = HDFS.isDirectory(new Path(path));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return isDirectory;
    }

    /**
     * Return the list of files and subdirectories on any directory
     */
    public List<String> list(String path)
    {
        List<String> files = new ArrayList<>();
        try {
            RemoteIterator<LocatedFileStatus> iterator = HDFS.listFiles(new Path(path), false);
            while (iterator.hasNext()){
                files.add(iterator.next().getPath().getName());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return files;
    }
}
