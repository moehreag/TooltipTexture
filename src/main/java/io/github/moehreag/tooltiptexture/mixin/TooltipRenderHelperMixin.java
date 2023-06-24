package io.github.moehreag.tooltiptexture.mixin;

import io.github.moehreag.tooltiptexture.TooltipTexture;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.tooltip.TooltipRenderHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TooltipRenderHelper.class)
public class TooltipRenderHelperMixin {

	@Inject(method = "renderRectangle", at = @At("HEAD"), cancellable = true)
	private static void tooltiptexture$drawBackground(GuiGraphics graphics, int x, int y, int width, int height, int z, int color, CallbackInfo ci){
		graphics.drawTexture(TooltipTexture.BACKGROUND_TEXTURE, x, y, z, 0, 0, width, height, width, height);

		ci.cancel();
	}
}
