package top.vmctcn.vmtranslationupdate.screen;

import java.lang.reflect.Method;

public class GlStateManagerHelper {
    private static final Method ENABLE_BLEND;
    private static final Method DISABLE_BLEND;

    static {
        Class<?> GL_STATE_MANAGER_CLASS;
        try {
            // >= 1.21.5
            GL_STATE_MANAGER_CLASS = Class.forName("com.mojang.blaze3d.opengl.GlStateManager");
        } catch (ClassNotFoundException e) {
            try {
                GL_STATE_MANAGER_CLASS = Class.forName("com.mojang.blaze3d.platform.GlStateManager");
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException("Could not find GlStateManager class", ex);
            }
        }

        try {
            ENABLE_BLEND = GL_STATE_MANAGER_CLASS.getDeclaredMethod("_enableBlend");
            DISABLE_BLEND = GL_STATE_MANAGER_CLASS.getDeclaredMethod("_disableBlend");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to initialize GlStateManagerHelper", e);
        }
    }

    public static void enableBlend() {
        try {
            ENABLE_BLEND.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke _enableBlend", e);
        }
    }

    public static void disableBlend() {
        try {
            DISABLE_BLEND.invoke(null);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke _disableBlend", e);
        }
    }
}
