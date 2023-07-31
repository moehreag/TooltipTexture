package io.github.moehreag.tooltiptexture.mixin;

import io.github.moehreag.tooltiptexture.TooltipTexture;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.tooltip.TooltipRenderHelper;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TooltipRenderHelper.class)
public abstract class TooltipRenderHelperMixin {

	@Shadow
	@Final
	private static int BACKGROUND_COLOR;
	@Shadow
	@Final
	private static int TOP_BACKGROUND_COLOR;
	@Shadow
	@Final
	private static int BOTTOM_BACKGROUND_COLOR;

	@Shadow
	private static void renderRectangle(GuiGraphics graphics, int x, int y, int width, int height, int z, int color) {
		throw new UnsupportedOperationException();
	}

	@Shadow
	private static void renderBorderGradient(GuiGraphics graphics, int x, int y, int width, int height, int z, int startColor, int endColor) {
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

	@Inject(method = "renderBackground",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/gui/tooltip/TooltipRenderHelper;renderHorizontalLine(Lnet/minecraft/client/gui/GuiGraphics;IIIII)V",
			ordinal = 0
		),
		locals = LocalCapture.CAPTURE_FAILEXCEPTION, cancellable = true)
	private static void tooltiptexture$drawBackgroundImage(GuiGraphics graphics, int x0, int y0, int width0, int height0, int z, CallbackInfo ci, int x, int y, int width, int height) {

		boolean background;
		background = drawStretchedTexture(graphics, TooltipTexture.BACKGROUND, x, y, z, width, height);

		if (!background) {
			renderRectangle(graphics, x, y, width, height, z, BACKGROUND_COLOR);
		}

		boolean border;
		border = drawStretchedTexture(graphics, TooltipTexture.BORDER_LEFT, x, y + 1, z, 1, height - 2);
		border = drawStretchedTexture(graphics, TooltipTexture.BORDER_RIGHT, x + width - 1, y + 1, z, 1, height - 2) || border;
		border = drawStretchedTexture(graphics, TooltipTexture.BORDER_TOP, x, y, z, width, 1) || border;
		border = drawStretchedTexture(graphics, TooltipTexture.BORDER_BOTTOM, x, y + height - 1, z, width, 1) || border;

		if (!border) {
			renderBorderGradient(graphics, x, y + 1, width, height, z, TOP_BACKGROUND_COLOR, BOTTOM_BACKGROUND_COLOR);
		}

		boolean outerBorder;
		outerBorder = drawStretchedTexture(graphics, TooltipTexture.BORDER_OUTER_TOP, x, y - 1, z, width, 1);
		outerBorder = drawStretchedTexture(graphics, TooltipTexture.BORDER_OUTER_BOTTOM, x, y + height, z, width, 1) || outerBorder;
		outerBorder = drawStretchedTexture(graphics, TooltipTexture.BORDER_OUTER_LEFT, x - 1, y, z, 1, height) || outerBorder;
		outerBorder = drawStretchedTexture(graphics, TooltipTexture.BORDER_OUTER_RIGHT, x + width, y, z, 1, height) || outerBorder;

		if (!outerBorder) {
			renderHorizontalLine(graphics, x - 1, y, width, z, BACKGROUND_COLOR);
			renderHorizontalLine(graphics, x, y + height, width, z, BACKGROUND_COLOR);
			renderVerticalLine(graphics, x - 1, y, height, z, BACKGROUND_COLOR);
			renderVerticalLine(graphics, x + width, y, height, z, BACKGROUND_COLOR);
		}

		ci.cancel();
	}

	@Unique
	private static boolean drawStretchedTexture(GuiGraphics graphics, Identifier texture, int x, int y, int z, int width, int height) {
		if (MinecraftClient.getInstance().getResourceManager().getResource(texture).isPresent()) {
			graphics.drawTexture(texture, x, y, z, 0, 0, width, height, width, height);
			return true;
		}
		return false;
	}
}
