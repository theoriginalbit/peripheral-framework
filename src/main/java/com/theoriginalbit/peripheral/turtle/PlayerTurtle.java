/**
 * Copyright 2014-2015 Joshua Asbury (@theoriginalbit)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.theoriginalbit.peripheral.turtle;

import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.turtle.ITurtleAccess;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;

/**
 * @author Joshua Asbury (@theoriginalbit)
 */
public class PlayerTurtle extends FakePlayer {
    public PlayerTurtle(ITurtleAccess turtle) {
        super((WorldServer) turtle.getWorld(), new GameProfile(null, "MoarPeripheralsTurtle"));
        final ChunkCoordinates coordinates = turtle.getPosition();
        setPosition(coordinates.posX + 0.5d, coordinates.posY + 0.5d, coordinates.posZ + 0.5d);
    }
}
