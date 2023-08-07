package io.github.moehreag.tooltiptexture.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.resources.ResourceLocation;
import io.github.moehreag.tooltiptexture.TooltipTextureCommon;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TooltipRenderUtil.class)
public class TooltipRenderUtilMixin {
    @Shadow
    @Final
    private static int BACKGROUND_COLOR;
    @Shadow
    @Final
    private static int BORDER_COLOR_TOP;
    @Shadow
    @Final
    private static int BORDER_COLOR_BOTTOM;

    @Shadow
    private static void renderRectangle(GuiGraphics graphics, int x, int y, int width, int height, int z, int color) {
        throw new UnsupportedOperationException();
    }

    @Shadow
    private static void renderFrameGradient(GuiGraphics graphics, int x, int y, int width, int height, int z, int startColor, int endColor) {
        throw new UnsupportedOperationException();
    }

    @Shadow
    private static void renderHorizontalLine(GuiGraphics graphics, int x, int y, int length, int z, int color) {
        throw new UnsupportedOperationException();
    }

    @Shadow
    private static void renderVerticalLine(GuiGraphics graphics, int x, int y, int length, int z, int color) {
        throw new UnsupportedOperationException();
    }

    @Inject(method = "renderTooltipBackground",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderHorizontalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V",
                    ordinal = 0
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION,
            cancellable = true
    )
    private static void tooltiptexture$renderTooltipBackground(GuiGraphics graphics, int x0, int y0, int width0, int height0, int z, CallbackInfo ci, int x, int y, int width, int height) {

        boolean background;
        background = drawStretchedTexture(graphics, TooltipTextureCommon.BACKGROUND, x, y, z, width, height);

        if (!background) {
            renderRectangle(graphics, x, y, width, height, z, BACKGROUND_COLOR);
        }

        boolean border;
        border = drawStretchedTexture(graphics, TooltipTextureCommon.BORDER_LEFT, x, y + 1, z, 1, height - 2);
        border = drawStretchedTexture(graphics, TooltipTextureCommon.BORDER_RIGHT, x + width - 1, y + 1, z, 1, height - 2) || border;
        border = drawStretchedTexture(graphics, TooltipTextureCommon.BORDER_TOP, x, y, z, width, 1) || border;
        border = drawStretchedTexture(graphics, TooltipTextureCommon.BORDER_BOTTOM, x, y + height - 1, z, width, 1) || border;

        if (!border) {
            renderFrameGradient(graphics, x, y + 1, width, height, z, BORDER_COLOR_TOP, BORDER_COLOR_BOTTOM);
        }

        boolean outerBorder;
        outerBorder = drawStretchedTexture(graphics, TooltipTextureCommon.BORDER_OUTER_TOP, x, y - 1, z, width, 1);
        outerBorder = drawStretchedTexture(graphics, TooltipTextureCommon.BORDER_OUTER_BOTTOM, x, y + height, z, width, 1) || outerBorder;
        outerBorder = drawStretchedTexture(graphics, TooltipTextureCommon.BORDER_OUTER_LEFT, x - 1, y, z, 1, height) || outerBorder;
        outerBorder = drawStretchedTexture(graphics, TooltipTextureCommon.BORDER_OUTER_RIGHT, x + width, y, z, 1, height) || outerBorder;

        if (!outerBorder) {
            renderHorizontalLine(graphics, x, y - 1, width, z, BACKGROUND_COLOR);
            renderHorizontalLine(graphics, x, y + height, width, z, BACKGROUND_COLOR);
            renderVerticalLine(graphics, x - 1, y, height, z, BACKGROUND_COLOR);
            renderVerticalLine(graphics, x + width, y, height, z, BACKGROUND_COLOR);
        }

        ci.cancel();
    }

    @Unique
    private static boolean drawStretchedTexture(GuiGraphics graphics, ResourceLocation texture, int x, int y, int z, int width, int height) {
        if (Minecraft.getInstance().getResourceManager().getResource(texture).isPresent()) {
            graphics.blit(texture, x, y, z, 0, 0, width, height, width, height);
            return true;
        }
        return false;
    }
}
