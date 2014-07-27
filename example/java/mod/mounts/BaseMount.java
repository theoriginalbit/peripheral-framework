package mod.mounts;

import com.theoriginalbit.minecraft.framework.peripheral.interfaces.IPFMount;
import mod.ExampleMod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This is a basic IPFMount
 *
 * @author theoriginalbit
 */
public abstract class BaseMount implements IPFMount {

    @Override
    public boolean exists(String path) throws IOException {
        return new File(new File(getMountLocation()), path).exists();
    }

    @Override
    public boolean isDirectory(String path) throws IOException {
        return new File(new File(getMountLocation()), path).isDirectory();
    }

    @Override
    public void list(String path, List<String> contents) throws IOException {
        File dir = new File(new File(getMountLocation()), path);
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
        return new FileInputStream(new File(new File(getMountLocation()), path));
    }

}
