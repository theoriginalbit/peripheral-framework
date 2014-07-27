package mod.mounts;

import mod.ExampleMod;

/**
 * This is a basic IPFMount
 *
 * @author theoriginalbit
 */
public class SpecialMount extends BaseMount {

    @Override
    public String getMountLocation() {
        return ExampleMod.MOUNT_PATH + "special/";
    }

}
