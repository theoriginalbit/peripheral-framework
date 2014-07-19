Peripheral Framework
====================

An open-source framework that has the aim of allowing developers to implement their ComputerCraft peripherals faster, easier, and cleaner; allowing them to focus more on developing their content.

Why Use This?
-------------

- Good for developers;<sup>1</sup>
- Good for users;<sup>2</sup>

<sup>1.</sup> This framework removes the need to define any of ComputerCraft's IPeripheral methods, thus decluttering your classes and making development quicker and easier.
<sup>2.</sup> Better formatted error messages when the wrong data type, or number of arguments, is provided to your method from the Lua program. e.g. `expected string, got number`

'Installation'
--------------

**Important this framework is intended for ComputerCraft 1.63 and any subsequent versions which make use of the same API**

To use this framework you need to copy the contents of the `src` directory into your project. This includes the much needed ComputerCraft API as well as the source for this framework.

It is suggested that you annotate your TileEntities with [OpenPeripheral's Ignore annotation](https://github.com/OpenMods/OpenPeripheral/blob/master/src/main/java/openperipheral/api/Ignore.java) so that if you implement anything such as IInventory on your TileEntity, OpenPeripheral will not attempt to claim the peripheral as its own.

I was going to type up full instructions here on how to use this framework, however I since created a heavily documented example mod, which demo's the usage much better, no point in repeating myself in how to use this framework, so I suggest you take a look in the `example` folder.

License
-------

The Peripheral-Framework developed by Joshua Asbury (@theoriginalbit) is distributed under the terms of the GPLv3 License, which can be found under the name LICENSE in the distribution.