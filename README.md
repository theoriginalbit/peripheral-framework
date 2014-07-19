Peripheral Framework
====================

**IMPORTANT! This framework is intended for ComputerCraft 1.63 and any subsequent versions which make use of the same API**

An open-source framework that has the aim of allowing developers to implement their ComputerCraft peripherals faster, easier, and cleaner; allowing them to focus more on developing their content.

Why Use This?
-------------

- Good for developers<sup>1</sup>;
- Good for users<sup>2</sup>;


<sup>1</sup> This framework removes the need to define any of ComputerCraft's IPeripheral methods, thus decluttering your classes and making development quicker and easier.

<sup>2</sup> Better formatted error messages when the wrong data type, or number of arguments, is provided to your method from the Lua program. e.g. `expected string, got number`

Installation for Developers
--------------

As a developer, installing this framework is as simple as copying the contents of the `src` directory into your project: this includes dan200's ComputerCraft API if you do not have it installed to your project already.

It is suggested that you annotate your TileEntities with [OpenPeripheral's Ignore annotation](https://github.com/OpenMods/OpenPeripheral/blob/master/src/main/java/openperipheral/api/Ignore.java) so that if you implement anything such as IInventory on your TileEntity, OpenPeripheral will not attempt to claim the peripheral as its own.

If you'd like to get an example of using this framework, a demo mod has been created [in the example](https://github.com/theoriginalbit/Peripheral-Framework/tree/master/example) folder. The documentation there should get you up and running with relative ease.


Installation for Users
--------------

If you are not a developer, you can install this framework by downloading the [current release](https://github.com/theoriginalbit/Peripheral-Framework/releases) and dropping it into your mods folder<sup>1</sup>. 

<sup>1</sup> There aren't actually any releases yet! Check back later and hopefully there will.


License
-------

The Peripheral-Framework developed by Joshua Asbury (@theoriginalbit) is distributed under the terms of the GPLv3 License, which can be found under the name LICENSE in the distribution.