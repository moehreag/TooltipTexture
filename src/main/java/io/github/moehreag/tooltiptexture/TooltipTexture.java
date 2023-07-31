package io.github.moehreag.tooltiptexture;

import net.minecraft.util.Identifier;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class TooltipTexture implements ClientModInitializer {

	// Center Background filling
	public static Identifier BACKGROUND = new Identifier("tooltiptexture", "background.png");

	// Inner Borders
	public static Identifier BORDER_LEFT = new Identifier("tooltiptexture", "border_left.png");
	public static Identifier BORDER_TOP = new Identifier("tooltiptexture", "border_top.png");
	public static Identifier BORDER_BOTTOM = new Identifier("tooltiptexture", "border_bottom.png");
	public static Identifier BORDER_RIGHT = new Identifier("tooltiptexture", "border_right.png");

	// Outer Borders
	public static Identifier BORDER_OUTER_LEFT = new Identifier("tooltiptexture", "border_outer_left.png");
	public static Identifier BORDER_OUTER_TOP = new Identifier("tooltiptexture", "border_outer_top.png");
	public static Identifier BORDER_OUTER_BOTTOM = new Identifier("tooltiptexture", "border_outer_bottom.png");
	public static Identifier BORDER_OUTER_RIGHT = new Identifier("tooltiptexture", "border_outer_right.png");

	@Override
	public void onInitializeClient(ModContainer mod) {

	}
}
