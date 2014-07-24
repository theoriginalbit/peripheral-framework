package mod;

import dan200.computercraft.api.filesystem.IMount;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This is a basic ComputerCraft IMount
 *
 * @author theoriginalbit
 */
public class ExampleMount implements IMount {

    @Override
    public boolean exists(String path) throws IOException {
        System.out.println("exists");
        System.out.println(new File(new File(ExampleMod.MOUNT_PATH), path).getAbsolutePath());
        System.out.println(new File(new File(ExampleMod.MOUNT_PATH), path).exists());
        return new File(new File(ExampleMod.MOUNT_PATH), path).exists();
    }

    @Override
    public boolean isDirectory(String path) throws IOException {
        System.out.println("isDir");
        System.out.println(new File(new File(ExampleMod.MOUNT_PATH), path).getAbsolutePath());
        System.out.println(new File(new File(ExampleMod.MOUNT_PATH), path).exists());
        return new File(new File(ExampleMod.MOUNT_PATH), path).isDirectory();
    }

    @Override
    public void list(String path, List<String> contents) throws IOException {
        System.out.println("list");
        System.out.println(new File(new File(ExampleMod.MOUNT_PATH), path).getAbsolutePath());
        System.out.println(new File(new File(ExampleMod.MOUNT_PATH), path).exists());
        File dir = new File(new File(ExampleMod.MOUNT_PATH), path);
        for (File file : dir.listFiles()) {
            contents.add(file.getName());
        }
    }

    @Override
    public long getSize(String path) throws IOException {
        return 0;
    }

    @Override
    public InputStream openForRead(String path) throws IOException {
        System.out.println(new File(new File(ExampleMod.MOUNT_PATH), path).getAbsolutePath());
        return new FileInputStream(new File(new File(ExampleMod.MOUNT_PATH), path));
    }
}
