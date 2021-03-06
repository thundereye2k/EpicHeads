package com.songoda.epicheads.volatilecode.reflection.craftbukkit;

import java.lang.reflect.Field;

import com.songoda.epicheads.util.Checks;
import org.bukkit.Bukkit;
import org.bukkit.command.*;

import com.songoda.epicheads.util.Checks;
import com.songoda.epicheads.volatilecode.reflection.ReflectObject;
import com.songoda.epicheads.volatilecode.reflection.ReflectionUtils;

public class CraftServer extends ReflectObject {
    
    public static Class<?> CraftServerClass;
    public static Field SimpleCommandMapField;
    
    static {
        CraftServerClass = ReflectionUtils.getCraftBukkitClass("CraftServer");
        Checks.ensureNonNull(CraftServerClass, "CraftServerClass");

        for (Field f : CraftServerClass.getDeclaredFields()) {
            if (org.bukkit.command.CommandMap.class.isAssignableFrom(f.getType())) {
                SimpleCommandMapField = f;
                SimpleCommandMapField.setAccessible(true);
                break;
            }
        }
    }
    
    public CraftServer(Object handle) {
        super(handle);
    }
    
    public SimpleCommandMap getCommandMap() {
        try {
            return (SimpleCommandMap) SimpleCommandMapField.get(getHandle());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static CraftServer get() {
        return new CraftServer(Bukkit.getServer());
    }
    
}
