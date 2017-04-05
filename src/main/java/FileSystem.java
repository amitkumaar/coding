/**
 * Created by amitkuma on 4/4/17.
 */
public interface FileSystem {
    Iterable<String> ls();
    /**
     * Support nested paths, eg., /a/b/c
     */
    boolean mkdir(String path);
    /**
     * Return the absolute path
     */
    String touch(String path);
    /**
     * Support nested paths, return the absolute path
     */
    String cd(String path);

    String pwd();

    boolean rm(String path, boolean recursive);
}
