package org.lavaelytraunfix;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Shadow
	protected abstract boolean canGlide();

	@Inject(
			method = "tryToStartFallFlying",
			at = @At("HEAD"),
			cancellable = true
	)
	private void lavaElytraUnfix(CallbackInfoReturnable<Boolean> cir) {
		Player player = (Player) (Object) this;

		if (player.isInLava()
				&& !player.isFallFlying()
				&& this.canGlide()) {

			player.startFallFlying();

			cir.setReturnValue(true);
		}
	}
}