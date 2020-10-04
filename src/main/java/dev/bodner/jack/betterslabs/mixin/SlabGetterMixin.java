package dev.bodner.jack.betterslabs.mixin;

import dev.bodner.jack.betterslabs.Betterslabs;
import dev.bodner.jack.betterslabs.client.BetterslabsClient;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import virtuoel.statement.api.StateRefresher;

@Mixin(Registry.class)
public class SlabGetterMixin {

    @Inject(at = @At("TAIL"), method = "register(Lnet/minecraft/util/registry/Registry;Lnet/minecraft/util/Identifier;Ljava/lang/Object;)Ljava/lang/Object;")
    private static <V, T extends V> void register(Registry<V> registry, Identifier id, T entry, CallbackInfoReturnable<T> cir) {
        if (entry.getClass() == SlabBlock.class){
            BetterslabsClient.slabList.add(id);
            StateRefresher.INSTANCE.addBlockProperty((Block) entry, Properties.AXIS, Direction.Axis.Y);
            StateRefresher.INSTANCE.reorderBlockStates();
        }
    }
}
