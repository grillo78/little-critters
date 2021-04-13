package com.grillo78.littlecritters.client.util;

import com.grillo78.littlecritters.LittleCritters;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import static org.lwjgl.opengl.GL11.GL_QUADS;

public class ModRenderTypes extends RenderType {

    private static final RenderType SCREEN = RenderType.create(LittleCritters.MOD_ID + ":screen_texture", DefaultVertexFormats.NEW_ENTITY, GL_QUADS, 256, true, false, RenderType.State.builder().setTexturingState(ScreenTextureState.instance()).setLightmapState(RenderState.LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(false));

    public ModRenderTypes(String nameIn, VertexFormat formatIn, int drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
        super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
    }

    public static RenderType getBumLayer(ResourceLocation locationIn) {
        RenderState.TextureState renderstate$texturestate = new RenderState.TextureState(locationIn, false, false);
        return create("eyes", DefaultVertexFormats.NEW_ENTITY, 7, 256, false, true,
                RenderType.State.builder().setTextureState(renderstate$texturestate).setTransparencyState(ADDITIVE_TRANSPARENCY)
                        .setAlphaState(DEFAULT_ALPHA).setWriteMaskState(COLOR_WRITE).setFogState(BLACK_FOG).createCompositeState(false));
    }

    public static RenderType getScreen() {
        return SCREEN;
    }
}
