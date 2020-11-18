package dev.bodner.jack.betterslabs;

import dev.bodner.jack.betterslabs.block.SlabBlockMod;
import dev.bodner.jack.betterslabs.component.Components;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Betterslabs implements ModInitializer {

    public static final SlabBlockMod TESTSLAB = new SlabBlockMod(FabricBlockSettings.of(Material.STONE, MaterialColor.STONE).requiresTool().strength(2.0F, 6.0F));
    public static final Identifier PLACE_MODE_PACKET_ID = new Identifier("betterslabs","placemode");

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("betterslabs","test_slab"),TESTSLAB);
        Registry.register(Registry.ITEM,new Identifier("betterslabs","test_slab"),new BlockItem(TESTSLAB, new Item.Settings()));

        ServerSidePacketRegistry.INSTANCE.register(PLACE_MODE_PACKET_ID, (packetContext, packetByteBuf) -> {
                    packetContext.getTaskQueue().execute(() -> {
                        Components.MODE_KEY.get(packetContext.getPlayer()).incrementPlaceMode();
                        Components.MODE_KEY.sync(packetContext.getPlayer());
                    });
                });
    }
}
