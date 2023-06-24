package io.github.moehreag.tooltiptexture;

import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class TooltipTexture implements ClientModInitializer {

	public static Identifier BACKGROUND_TEXTURE = new Identifier("tooltiptexture", "background.png");

	@Override
	public void onInitializeClient(ModContainer mod) {

	}
}
