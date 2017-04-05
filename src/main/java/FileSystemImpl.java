import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * Created by amitkuma on 4/4/17.
 */
public class FileSystemImpl implements FileSystem {

    ConcurrentMap<FileNode , Set<FileNode>> fs = new ConcurrentHashMap<>();
    FileNode currentDirectory;
    
    @Override
    public Iterable<String> ls() {
        if(currentDirectory == null) throw new IllegalArgumentException("file system not setup properly");
        return fs.get(currentDirectory).stream().map(fileNode -> fileNode.getName()).collect(Collectors.toList());
    }

    @Override
    public boolean mkdir(String path) {
         FileNode fileNode = new FileNode(path,FileType.DIR);
         if(fs.containsKey(fileNode)) return false;
         fs.put(fileNode, new HashSet<>());
         return true;
    }

    @Override
    public String touch(String path) {
        FileNode fileNode = new FileNode(path,FileType.FILE);
        Set<FileNode> fileNodes = fs.get(currentDirectory);
        if(fileNodes == null){
            fs.put(currentDirectory, new HashSet<>(Arrays.asList(fileNode)));
        }else{
            fileNodes.add(fileNode);

        }

        return path;
    }

    @Override
    public String cd(String path) {
        FileNode fileNode = new FileNode(path, FileType.DIR);
        if(fs.get(fileNode)==null)  throw new IllegalArgumentException("directory does not exists.");
        this.currentDirectory = fileNode;
        return path;
    }

    @Override
    public String pwd() {
        return currentDirectory.getName();
    }

    @Override
    public boolean rm(String path, boolean recursive) {
        FileNode fileNode = new FileNode(path);
        if(fs.get(fileNode) != null){
          fs.remove(fileNode);
          return true;
        }
        return false;
    }

    static class FileNode{
        final String name;
        final FileType fileType;

        public FileNode(String name) {
            this(name,FileType.DIR);

        }

        public FileNode(String name, FileType fileType) {
            this.name = name;
            this.fileType = fileType;
        }

        public String getName() {
            return name;
        }

        public FileType getFileType() {
            return fileType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FileNode fileNode = (FileNode) o;

            return name.equals(fileNode.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }
}
