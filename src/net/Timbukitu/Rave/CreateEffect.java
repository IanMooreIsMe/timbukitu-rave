package net.Timbukitu.Rave;

import java.lang.reflect.Field;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.entity.CraftPlayer;
import net.minecraft.server.v1_7_R3.PacketPlayOutWorldParticles;

public class CreateEffect {
	public void createEffect(CraftPlayer player, String nameOfEffect, Location location, float xOffset, float yOffset,
            float zOffset, float effectSpeed, int amountOfParticles)
    {
		float playersX = (float) location.getX();
		float playersY = (float) location.getY();
		float playersZ = (float) location.getZ();
        // Make an instance of the packet!
        PacketPlayOutWorldParticles sPacket = new PacketPlayOutWorldParticles();
        for (Field field : sPacket.getClass().getDeclaredFields())
        {
            try
            {
                // Get those fields we need to be accessible!
                field.setAccessible(true);
                String fieldName = field.getName();
                // Set them to what we want!
                switch (fieldName)
                {
                case "a":
                    field.set(sPacket, nameOfEffect);
                    break;
                case "b":
                    field.setFloat(sPacket, playersX);
                    break;
                case "c":
                    field.setFloat(sPacket, playersY);
                    break;
                case "d":
                    field.setFloat(sPacket, playersZ);
                    break;
                case "e":
                    field.setFloat(sPacket, xOffset);
                    break;
                case "f":
                    field.setFloat(sPacket, yOffset);
                    break;
                case "g":
                    field.setFloat(sPacket, zOffset);
                    break;
                case "h":
                    field.setFloat(sPacket, effectSpeed);
                    break;
                case "i":
                    field.setInt(sPacket, amountOfParticles);
                    break;
                }
            } catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        player.getHandle().playerConnection.sendPacket(sPacket);
    }
}


